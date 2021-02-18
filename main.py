from Image import Image
import cv2
from helper_funcs import *
from skimage.segmentation import mark_boundaries
# from pylab import *
if __name__ == '__main__':
    IMAGE_PATH = "./Data/image_0721.jpg"
    image = Image(IMAGE_PATH)
    # image.rgb_to_lab()
    gray = image.rgb_to_gray(image.image)
    cv2.imwrite('./gray.jpg', gray)
    # p = get_interest_points_SURF(gray).astype(int)

    # print(p)
    # import milk
    # # descrs = spoints[:,5:]
    # k = 5
    # values, _  =milk.kmeans(p, k)
    # colors = np.array([(255-52*i,25+52*i,37**i % 101) for i in range(k)])
    # f2 = surf.show_surf(f, p[:100], values, colors)
    # imshow(f2)
    # show()
    # marked_img =  mark_boundaries(gray, p)
    # # cv2.imshow("SURF", np.hstack([marked_img]))
    # # cv2.imshow("Gray", gray)  # For B Channel
    # # cv2.waitKey(0)
    # # cv2.destroyAllWindows()
    # # print(len(image.lab))
    # # descritize(16, [image], save=True)
    # # image.show_lab_channels()
    # image.get_slic_superpixels(show=False) 

    # segments = calculate_slic_superpixels(image.image)
    # show_slic(image.image, segments)