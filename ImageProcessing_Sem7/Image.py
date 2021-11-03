import cv2
import sys
from skimage.segmentation import slic
from skimage.segmentation import mark_boundaries
from skimage.util import img_as_float
from skimage import io
from skimage.filters import gabor
from mahotas.features import surf
import numpy as np
"""A Class that contains the basic methods needed to process an image in order to 
    color it automatically. USES Python-OpenCV
    Methods:
    rgb_to_lab(): Converts an image represented with the rgb model to the LAB model.
    get_lab_channels(lab): takes a lab image as argument and gets it's L,A,B channels respectively.
    show_lab_channels(): Show the images created from the lab channels and also the image in lab model.    
"""

class Image(object):
    def __init__(self, image_path):
        self.image_path = image_path
        self.image = cv2.imread(self.image_path)
    #ERWTHMA i)
    @classmethod
    def rgb_to_lab(cls, image):
        #input = cv2.imread(self.image_path)
        lab = cv2.cvtColor(image, cv2.COLOR_BGR2LAB)
        return lab
    @classmethod
    def rgb_to_lab_cls(cls, img):
        lab = cv2.cvtColor(img, cv2.COLOR_BGR2LAB)
        return lab
    @classmethod
    def get_lab_channels(cls, lab):
        L,A,B = cv2.split(lab)
        return L,A,B
    
    @classmethod
    def rgb_to_gray_cls(cls, image):
        gray = cv2.cvtColor(image, cv2.COLOR_RGB2GRAY)
        return gray
    @classmethod
    def lab_to_rgb(cls, lab):
        return cv2.cvtColor(lab, cv2.COLOR_LAB2LRGB)
    def show_lab_channels(self):
        try:
            cv2.imshow("l*a*b", self.lab)
            cv2.imshow("L_Channel", self.L)  # For L Channel
            cv2.imshow("A_Channel", self.A)  # For A Channel (Here's what You need)
            cv2.imshow("B_Channel", self.B)  # For B Channel
            cv2.waitKey(0)
            cv2.destroyAllWindows()
        except AttributeError:
            print("The image hasnt been converted to the lab color model yet...")
            sys.exit(0)
    
    def show_image(image):
        cv2.imshow("Image", image)  # For B Channel
        cv2.waitKey(0)
        cv2.destroyAllWindows()

    @classmethod
    def get_slic_segments(cls, image, num_of_segments=100, gray=False):
        if not gray:
            return slic(image, n_segments=num_of_segments)
        else:
            return slic(image, n_segments=num_of_segments, compactness=0.1)
            


#code from: https://www.pyimagesearch.com/2014/12/29/accessing-individual-superpixel-segmentations-python/
    @classmethod
    def get_sp_list(cls, image, image_is_gray=False):
        superpixels_list = []
        segments = cls.get_slic_segments(image, gray=image_is_gray)
        for(i, segVal) in enumerate(np.unique(segments)):
            mask = np.zeros(image.shape[:2], dtype="uint8")
            mask[segments == segVal] = 255
            superpixel = cv2.bitwise_and(image, image, mask=mask)
            superpixels_list.append(superpixel)
        return superpixels_list
    @classmethod
    def get_surf(cls,image):
        return surf.surf(image)
    
        # Extracting SURF for each superpixel and after making it grayscale
    @classmethod
    def get_surf_for_sps(cls, sp_list, gray=False):
        print("Extracting SURF features for each superpixel...")
        SURF_list = []
        for superpixel in sp_list:
            if not gray:
                gray_superpixel = cls.rgb_to_gray_cls(superpixel)
            else:
                gray_superpixel = superpixel
            
            ip = cls.get_surf(gray_superpixel)
            SURF_list.append(ip)
        return SURF_list

    @classmethod
    def get_gabor(cls, image, freq=0.6):
        filter_real, filter_imaginary = gabor(image, frequency=freq)
        return filter_real

    @classmethod
    def get_gabor_for_sps(cls, superpixels_list, gray=False):
        print("Extracting Gabor features for each superpixel...")
        Gabor_list = []
        for superpixel in superpixels_list:
            if not gray:
                gray_superpixel = Image.rgb_to_gray_cls(superpixel)
            else:
                gray_superpixel = superpixel
            g = cls.get_gabor(gray_superpixel)
            Gabor_list.append(g)
        return Gabor_list
