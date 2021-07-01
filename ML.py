from sklearn.svm import LinearSVC
from sklearn.svm import SVC
from sklearn.cluster import KMeans
import numpy as np
import pickle
from os import path
import cv2
#ii)
def save_svm(superpixels_list, surf_list, gabor_list):
    print("Started fitting svm...")
    classifier = SVC(class_weight='balanced')
    prefitted_kmeans = load_kmeans()
    training_samples = []
    training_labels = []
    #get features
    for i in range(len(superpixels_list)):
        features = []
        # average of each pixel's SURF
        surf_sp = surf_list[i]
        surf_sp = np.average(surf_sp)
        surf_sp = np.nan_to_num(surf_sp)
        # average of each pixel's Gabor
        gabor_sp = gabor_list[i]
        gabor_sp = np.average(gabor_sp)
        gabor_sp = np.nan_to_num(gabor_sp)
        features.append(surf_sp)
        features.append(gabor_sp)

        # dominant color of the superpixel
        sp_color = superpixels_list[i]
        sp_color = cv2.cvtColor(sp_color, cv2.COLOR_RGB2LAB)
        sp_color = sp_color.reshape((sp_color.shape[0] * sp_color.shape[1]), 3)
        t = []
        #throw away sp arrays of incorect shape and vals
        for arr in sp_color:
            if not np.array_equal(arr, [0, 128, 128]):
                t.append([arr[1], arr[2]])
        sp_color = t
        if len(sp_color) == 0:
            continue
        sp_color = prefitted_kmeans.predict(sp_color)
        (values, counts) = np.unique(sp_color, return_counts=True)
        var = np.argmax(counts)
        sp_color = values[var]
        training_samples.append(features)
        training_labels.append(sp_color)
    fitted_svc = classifier.fit(training_samples, training_labels)
    pickle.dump(fitted_svc, open("SVC_pretrained.sav", 'wb'))
    print("finished...")


def load_svm():
    loaded_svc = pickle.load(open("SVC_pretrained.sav", 'rb'))
    return loaded_svc



def save_kmeans(lab_images,amount_of_clusters=16):
    print("Started fitting kmeans...")
    kmeans = KMeans(n_clusters=amount_of_clusters)
    # Reshaping lab images as feature vector
    ab_values = []
    for lab_image in lab_images:
        lab_image = lab_image.reshape((lab_image.shape[0] * lab_image.shape[1]), 3) #turn into a, b feature vector
        lab_image = np.delete(lab_image, 0, 1) #remove L channel 
        ab_values.extend(lab_image) # keep adding values to ab vals
    ab_values = np.array(ab_values)
    fitted = kmeans.fit(ab_values)
    print("finished...")
    pickle.dump(fitted, open("kmeans_prefitted.sav", 'wb'))
    


def load_kmeans():
    loaded_kmeans = pickle.load(open("kmeans_prefitted.sav", 'rb'))
    return loaded_kmeans
