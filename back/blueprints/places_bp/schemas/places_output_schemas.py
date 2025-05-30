from marshmallow import Schema, fields
from model.schemas.placeSchema import PlaceSchema
from model.schemas.placeImgSchema import PlaceImgSchema

class PlaceGet_OutputSchema(Schema):
    places = fields.List(fields.Nested(PlaceSchema))

class PlaceWithImage_OutputSchema(Schema):
    place = fields.Nested(PlaceSchema)
    image = fields.Nested(PlaceImgSchema)