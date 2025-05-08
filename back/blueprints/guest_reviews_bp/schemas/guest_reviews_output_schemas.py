from marshmallow import Schema, fields
from model.schemas.guestReviewSchema import GuestReviewSchema

class GuestReviewGetAll_OutputSchema(Schema):
    guest_reviews = fields.List(fields.Nested(GuestReviewSchema))