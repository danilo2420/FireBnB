from marshmallow import Schema, fields
from model.schemas.placeSchema import PlaceSchema

class PlaceGet_OutputSchema(Schema):
    places = fields.List(fields.Nested(PlaceSchema))