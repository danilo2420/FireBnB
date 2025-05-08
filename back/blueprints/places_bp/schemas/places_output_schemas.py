from marshmallow import Schema, fields
from model.schemas.placeSchema import PlaceSchema

class Success_OutputSchema(Schema):
    message = fields.Str()

class Get_OutputSchema(Schema):
    places = fields.List(fields.Nested(PlaceSchema))