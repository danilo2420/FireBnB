from marshmallow import Schema, fields
from model.schemas.placeImgSchema import PlaceImgSchema

class PlaceImgGetAll_OutputSchema(Schema):
    place_imgs = fields.List(fields.Nested(PlaceImgSchema))

class GetImgsForPlace_OutputSchema(Schema):
    place_imgs = fields.List(fields.Nested(PlaceImgSchema))