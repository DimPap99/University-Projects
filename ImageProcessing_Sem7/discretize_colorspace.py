from sklearn.cluster import KMeans
from mahotas.features import surf
import image_functions as imf
from skimage.filters import gabor
import pickle
import numpy as np
from os import path
from Image import Image

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



