# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class ExodusItem(scrapy.Item):
    title= scrapy.Field()
    link= scrapy.Field()
    desc= scrapy.Field()
    file_urls = scrapy.Field()
    #image_urls= scrapy.Field()
    images = scrapy.Field()
    pass
class YahooItem(scrapy.Item):
    title= scrapy.Field()
    link= scrapy.Field()
    desc= scrapy.Field()
    pass
