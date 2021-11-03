from sklearn.cluster import KMeans
import pickle
import numpy as np
from os import path
from Image import Image
from mahotas.features import surf
# import image_functions as imf
from skimage.filters import gabor
#ii)
def descritize(amt_of_clusters:int, lab_images:list, save=False):
    """
    Variables:
        num_of_colors: An int representing the amount of dominant colors we will accrue from k-means clustering
        lab_images: A list of Images converted into the Lab model.
    Use K-means to find the most dominant colors.
    """ 
    values  = list()
   
    
    for image in lab_images:
        image.lab = image.lab.reshape((image.lab.shape[0] * image.lab.shape[1]), 3)
        image.lab = np.delete(image.lab, 0, 1) # Remove L and keep a, b
        values.extend(image.lab)
    values = np.array(values)
    
    fit = KMeans(n_clusters=amt_of_clusters).fit(values)
    if save is True:
        pickle.dump(fit, open("./colors_kmeans.sav", 'wb'))


def load():
    load_obj = pickle.load(open("./colors_kmeans.sav", 'rb'))
    return load_obj



def get_interest_points_SURF(image):
    """
        Extracts SURF and gets the interest points
    """
    # interest_points = surf.surf(image)
    spoints = surf.surf(image, 4, 6, 2)
    # surf.show_surf(image, interest_points)
    # print(interest_points)
    return spoints



# Extracting SURF for each superpixel and after making it grayscale
def extract_surf_for_each_superpixel(superpixels_list, gray=False):
    print("Extracting SURF features...")
    surf_of_superpixels = []
    for superpixel in superpixels_list:
        if not gray:
            gray_superpixel = imf.rgb_to_gray(superpixel)
        else:
            gray_superpixel = superpixel
        points = extract_surf(gray_superpixel)
        surf_of_superpixels.append(points)
    print("SURF extraction finished!")
    return surf_of_superpixels


def extract_gabor(image, freq=0.6):
    filter_real, filter_imaginary = gabor(image, frequency=freq)
    return filter_real


# Extracting Gabor for each superpixel and after making it grayscale
def extract_gabor_for_each_superpixel(superpixels_list, gray=False):
    print("Extracting Gabor features...")
    gabor_of_superpixels = []
    for superpixel in superpixels_list:
        if not gray:
            gray_superpixel = imf.rgb_to_gray(superpixel)
        else:
            gray_superpixel = superpixel
        filters = extract_gabor(gray_superpixel)
        gabor_of_superpixels.append(filters)
    print("Gabor extraction finished!")
    return gabor_of_superpixels
