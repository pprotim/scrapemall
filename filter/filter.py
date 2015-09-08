from PIL import Image
import imagehash
import argparse
import shelve
import glob

ap = argparse.ArgumentParser()
ap.add_argument("-d", "--dataset", required = True,help = "path to input dataset of images")
ap.add_argument("-s", "--shelve", required = True,help = "output shelve database")
args = vars(ap.parse_args())

db = shelve.open(args["shelve"], writeback = True)

for imagePath in glob.glob(args["dataset"] + "/*.jpg"):
	filename = imagePath[imagePath.rfind("/") + 1:]
	image = Image.open(imagePath)
	h = str(imagehash.dhash(image))
        if db.has_key(h):
            print "Already there"
            image.save("../Duplicates_JPG/"+filename)
        else:
            image.save("../Unique_JPG/"+filename)
	db[h] = db.get(h, []) + [filename]

db.close()
