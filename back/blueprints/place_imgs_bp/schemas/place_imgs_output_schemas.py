from marshmallow import Schema, fields
from model.schemas.placeImgSchema import PlaceImgSchema

class Success_OutputSchema(Schema):
    message = fields.Str()

class GetAll_OutputSchema(Schema):
    place_imgs = fields.List(fields.Nested(PlaceImgSchema))

class GetImgsForPlace_OutputSchema(Schema):
    place_imgs = fields.List(fields.Nested(PlaceImgSchema))