from marshmallow import Schema, fields

class FavoriteListSchema(Schema):
    id = fields.Int()
    user_id = fields.Int()
    name = fields.Str()