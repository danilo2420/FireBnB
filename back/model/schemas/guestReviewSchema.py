from marshmallow import Schema, fields

class GuestReviewSchema(Schema):
    id = fields.Int()
    host_id = fields.Int()
    guest_id = fields.Int()
    date = fields.Date()
    stars = fields.Int()
    description = fields.Str()