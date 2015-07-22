import scrapy
from os import listdir
from os.path import isfile, join

from exodus.items import ExodusItem
class exodusSpider(scrapy.Spider):
	name="exodus"
	#allowed_domains=["in.yahoo.com"]
	
	#all files in the input dir
	mypath = "/home/cervere/sandbox/tutorial/urls1"
	onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]
	
	start_urls = []
	for ef in onlyfiles:
		with open(join(mypath,ef), "r") as ins:
   			for line in ins:
        			line = line.strip()	
				if not line.startswith("http"):	
					line = "http://" + line	
				#if ".com" not in line:
					#line = line + ".com"  	
				start_urls.append(line.replace(" ",""))
	
	print start_urls
	def parse(self,response):
		#filename = response.url.split("/")[-2]
		#with open(filename, 'a') as f:
			for sel in response.xpath('//img'):
       		     		item = ExodusItem()
            			item['title'] = sel.xpath('@alt').extract()
            			item['link'] = sel.xpath('@src').extract()
				file_urls = sel.xpath('@src').extract()
				item['file_urls'] = [x if x.startswith("http") else 'http://flhsmv.gov/dmv/specialtytags/' + x.replace("../","") for x in file_urls]
				yield item
  				#return item

