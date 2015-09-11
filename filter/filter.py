from PIL import Image
import os
import imagehash
import argparse
import shelve
import glob

ap = argparse.ArgumentParser()
ap.add_argument("-d", "--dataset", required = True,help = "path to input dataset of images")
ap.add_argument("-s", "--shelve", required = True,help = "output shelve database")
ap.add_argument("-f", "--ftype", required = False,help = "index images of type")
args = vars(ap.parse_args())

filetypes = ["jpg", "jpeg", "png", "gif"]
mask = [1,1,1,1]

UNIQUE = "./Unique"
DUPLICATE = "./Duplicates"

def index(folder, type="jpg", reindex=False):
    for imagePath in glob.glob(folder + "/*."+type):
        filename = imagePath[imagePath.rfind("/") + 1:]
	image = Image.open(imagePath)
	h = str(imagehash.dhash(image))
        if db.has_key(h):
            print "Already there, copying to duplicates"
            image.save(DUPLICATE+"/"+filename)
        elif not reindex:
            image.save(UNIQUE+"/"+filename)
	db[h] = db.get(h, []) + [filename]

def indexall(folder, reindex=False):
    for ftype in filetypes:
        index(folder, ftype, reindex)

if not os.path.exists(UNIQUE):
    os.makedirs(UNIQUE)
if not os.path.exists(DUPLICATE):
    os.makedirs(DUPLICATE)

db = shelve.open(UNIQUE+"/"+args["shelve"], writeback = True)

if not os.path.exists(UNIQUE+"/"+args["shelve"]):
    print "no shelve found in %s" % (UNIQUE+"/"+args["shelve"])
    print "re-indexing the folder"
    indexall(UNIQUE, reindex=True)
    
folder = args["dataset"]
if args.has_key("ftype"):
    ftype = args["ftype"]
    index(folder, ftype)
else:
    indexall(folder)

db.close()
