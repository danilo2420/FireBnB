from marshmallow import Schema, fields
from model.schemas.placeReviewSchema import PlaceReviewSchema

class PlaceReviewGetAll_OutputSchema(Schema):
    place_reviews = fields.List(fields.Nested(PlaceReviewSchema))