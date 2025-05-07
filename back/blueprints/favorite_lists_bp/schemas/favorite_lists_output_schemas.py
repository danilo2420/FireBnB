from marshmallow import Schema, fields
from model.schemas.placeSchema import PlaceSchema
from model.schemas.favoriteListSchema import FavoriteListSchema

class GetAll_OutputSchema(Schema):
    favorite_lists = fields.List(fields.Nested(FavoriteListSchema))

class GetPlaces_OutputSchema(Schema):
    places = fields.List(fields.Nested(PlaceSchema))