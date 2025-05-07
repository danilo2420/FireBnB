from marshmallow import Schema, fields

class PlaceReviewSchema(Schema):
    id = fields.Int()
    guest_id = fields.Int()
    place_id = fields.Int()
    date = fields.Date()
    description = fields.Str()
    stars = fields.Int()