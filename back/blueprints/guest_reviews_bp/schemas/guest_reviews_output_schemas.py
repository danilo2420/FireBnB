from marshmallow import Schema, fields
from model.schemas.guestReviewSchema import GuestReviewSchema

class Success_OutputSchema(Schema):
    message = fields.Str()

class GetAll_OutputSchema(Schema):
    guest_reviews = fields.List(fields.Nested(GuestReviewSchema))