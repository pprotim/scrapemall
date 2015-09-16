from PIL import Image
import sys
import os
import imagehash
import argparse
import shelve
import glob

ap = argparse.ArgumentParser()
ap.add_argument("-d", "--dataset", required = False,help = "path to input dataset of images")
ap.add_argument("-s", "--search", required = True,help = "image to search")
args = vars(ap.parse_args())

UNIQUE = "./Unique"
DUPLICATE = "./Duplicates"

imagePath = args["search"]

if not os.path.exists(imagePath):
    print "no image found : %s" % (imagePath)
    sys.exit(0)

image = Image.open(imagePath)
h = str(imagehash.dhash(image))

dir = ""

if args.has_key("dataset"):
    dir = args["dataset"] 
else:
    print "Dataset not specified"
    dir = UNIQUE

print "Searching in %s" % dir

db = shelve.open(dir+"/db.shelve", writeback = True)

if not os.path.exists(UNIQUE+"/"+args["shelve"]):
    print "no shelve found in %s" % (UNIQUE+"/"+args["shelve"])
    sys.exit(0)
    #indexall(UNIQUE, reindex=True)
    
if db.has_key(h):
    print "Already there"
else:
    print "Image not present"


db.close()
