import scrapy

from exodus.items import YahooItem
class yahooSpider(scrapy.Spider):
	name="yahoo"
	allowed_domains=["in.yahoo.com"]
	start_urls = ["https://in.yahoo.com/?p=us"]

	def parse(self,response):
		#filename = response.url.split("/")[-2]
		#with open(filename, 'a') as f:
			for sel in response.xpath('//img'):
       		     		item = YahooItem()
            			item['title'] = sel.xpath('@alt').extract()
            			item['link'] = sel.xpath('@src').extract()
  				yield item
