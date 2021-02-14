from Image import Image, calculate_slic_superpixels, show_slic


if __name__ == '__main__':
    IMAGE_PATH = "./Data/image_0721.jpg"
    image = Image(IMAGE_PATH)
    # image.rgb_to_lab()
  #  image.show_lab_channels()
    image.get_slic_superpixels( show=True) 
    segments = calculate_slic_superpixels(image.image)
    show_slic(image.image, segments)