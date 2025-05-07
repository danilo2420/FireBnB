from marshmallow import Schema, fields
from model.schemas.favoriteListSchema import FavoriteListSchema

class GetAll_OutputSchema(Schema):
    favorite_lists = fields.List(fields.Nested(FavoriteListSchema))