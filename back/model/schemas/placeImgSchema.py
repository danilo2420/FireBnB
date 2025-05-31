from marshmallow import Schema, fields

class PlaceImgSchema(Schema):
    id = fields.Int()
    place_id = fields.Int(required=True)
    title = fields.Str()
    img = fields.Str()