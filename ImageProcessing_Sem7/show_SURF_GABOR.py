from __future__ import print_function
import numpy as np
import mahotas as mh
from mahotas.features import surf
import cv2
from pylab import *
from Image import Image
from os import path
from helper_funcs import extract_gabor
#https://mahotas.readthedocs.io/en/latest/surf.html
image = Image('./data/test.jpg')
# image.rgb_to_lab()
gray = image.rgb_to_gray(image.image)
# f =  cv2.imread(self.image_path)
f = gray
spoints = surf.surf(f)
print(spoints)
print("Nr points:", len(spoints))

try:
    import milk
    descrs = spoints[:,5:]
    k = 5
    values, _  =milk.kmeans(descrs, k)
    colors = np.array([(255-52*i,25+52*i,37**i % 101) for i in range(k)])
except:
    values = np.zeros(100)
    colors = np.array([(255,0,0)])

f2 = surf.show_surf(f, spoints[:25], values, colors)
imshow(f2)
show()

g = extract_gabor(gray)
cv2.imshow("B_Channel", g)  
cv2.waitKey(0)
cv2.destroyAllWindows()