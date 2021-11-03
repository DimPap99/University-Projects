from ML import load_svm, save_svm, load_kmeans, save_kmeans
from os import path
import numpy as np
from Image import Image
import cv2
import warnings
import time,os
warnings.filterwarnings("ignore")


def show(original_img, gray_img, colors):
    # segments = slic(gray_img, n_segments=100, compactness=0.1)
    segments = Image.get_slic_segments(gray_img, gray=True)
    shape = gray_img.shape
    a_channel = []
    b_channel = []
    for i in range(shape[0]):
        rowA = []
        rowB = []
        for j in range(shape[1]):
            color = segments[i][j]
            rowA.append(colors[color][0])
            rowB.append(colors[color][1])
        a_channel.append(rowA)
        b_channel.append(rowB)
    a_channel = np.array(a_channel)
    b_channel = np.array(b_channel)
    predicted_image = np.stack((gray_img, a_channel, b_channel), axis=2)
    rgb = cv2.cvtColor(predicted_image, cv2.COLOR_LAB2LRGB)
    original_lab = cv2.cvtColor(original_img, cv2.COLOR_RGB2LAB)
    cv2.imshow("Original LAB - Colored LAB ", np.hstack([original_lab, predicted_image]))
    cv2.imshow("Original RGB - Predicted RGB", np.hstack([original_img,rgb]))
    cv2.imshow("Gray", gray_img)
    cv2.waitKey(0)


def extract_test_features(superpixels, surf, gabor):
    samples = []
    for i in range(len(superpixels)):
        features = []
        surf_of_sp = surf[i]
        surf_of_sp = np.average(surf_of_sp)
        surf_of_sp = np.nan_to_num(surf_of_sp)
        gabor_of_sp = gabor[i]
        gabor_of_sp = np.average(gabor_of_sp)
        gabor_of_sp = np.nan_to_num(gabor_of_sp)
        features.append(surf_of_sp)
        features.append(gabor_of_sp)
        samples.append(features)
    return samples


if __name__ == "__main__":
    import warnings
    # warnings.filterwarnings("ignore")

    SUPERPIXELS_NUM = 100
    DATASET_PATH = './data1'
    TEST_IMAGE = os.path.join(DATASET_PATH,"test.jpg")
    print("If you want to retrain everything change the RETRAIN variable to True...")
    time.sleep(3)
    RETRAIN = False
    if RETRAIN is True:
        # Load train image of castle
        rgb_images = []
        lab_images = []
        # Loading all training images
        for filename in os.listdir(DATASET_PATH):
            print(filename)
            if filename == 'test.jpg':
                continue
            image = cv2.imread(os.path.join(DATASET_PATH, filename))
            rgb_images.append(image)
            image = cv2.cvtColor(image, cv2.COLOR_RGB2LAB)
            lab_images.append(image)

        # Use kmeans to get colors. Pickle to reduce time between runs
        save_kmeans(lab_images)
        slic_list = []
        surf_list = []
        gabor_list = []

        for img in rgb_images:
            # get segments and features of every sp
            slic = Image.get_sp_list(img)
            slic_list.extend(slic)
            surf = Image.get_surf_for_sps(slic)
            surf_list.extend(surf)
            gabor = Image.get_gabor_for_sps(slic)
            gabor_list.extend(gabor)

        #train SVM and picke to save time between runs.
        save_svm(slic_list, surf_list, gabor_list)

    print(f"Beggining test on image: {TEST_IMAGE}")
    # Load anything that was pickled
    print("load kmeans, svm....")
    svc = load_svm()
    kmeans = load_kmeans()

    colored_img = cv2.imread(TEST_IMAGE)
    gray_img = cv2.cvtColor(colored_img, cv2.COLOR_RGB2GRAY)

    # get the needed components to make the test on our grayscale image
    slic = Image.get_sp_list(gray_img, image_is_gray=True)
    surf = Image.get_surf_for_sps(slic, True)
    gabor = Image.get_gabor_for_sps(slic, True)
    predictions = svc.predict(extract_test_features(slic, surf, gabor))
    print("predictions have been made...")
    colors = kmeans.cluster_centers_.astype("uint8")[predictions]
    show(colored_img, gray_img, colors)
