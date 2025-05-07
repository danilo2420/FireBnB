from marshmallow import Schema, fields
from model.schemas.placeReviewSchema import PlaceReviewSchema

class GetAll_OutputSchema(Schema):
    places = fields.List(fields.Nested(PlaceReviewSchema))