from marshmallow import Schema, fields
from model.schemas.placeSchema import PlaceSchema

class Success_OutputSchema(Schema):
    message = fields.Str()

class GetAll_OutputSchema(Schema):
    places = fields.List(fields.Nested(PlaceSchema))