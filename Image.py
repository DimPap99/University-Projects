import cv2
import sys
from skimage.segmentation import slic
from skimage.segmentation import mark_boundaries
from skimage.util import img_as_float
from skimage import io
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
    def rgb_to_lab(self):
        #input = cv2.imread(self.image_path)
        self.lab = cv2.cvtColor(self.image, cv2.COLOR_BGR2LAB)
        self.get_lab_channels(self.lab)
    def get_lab_channels(self, lab):
        self.L, self.A, self.B = cv2.split(lab)
        

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
    

    def get_slic_superpixels(self, show=False, num_of_segments=100):
        try:    
            # cv2.imshow('Reference Image', self.image)
            # cv2.waitKey(0)
            # cv2.destroyAllWindows()
            # image = img_as_float(self.image)
            # apply SLIC and extract (approximately) the supplied number
            # of segments
            self.segments = slic(self.image, n_segments=num_of_segments, sigma=0.5)
            # show the output of SLIC
            if show is True:
                marked_img =  mark_boundaries(self.image, self.segments)
                cv2.imshow("SLIC superpixels", np.hstack([marked_img]))
                cv2.waitKey(0)
        except AttributeError:
            print("The image hasnt been converted to lab yet...")

