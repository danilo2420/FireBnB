from marshmallow import Schema, fields, validates_schema, ValidationError
from model.schemas.rentingSchema import RentingSchema

class RentingGet_OutputSchema(Schema):
    rentings = fields.List(fields.Nested(RentingSchema))

class RentalPreview(Schema):
    place_id = fields.Int()
    name = fields.Str()
    type = fields.Str()
    start_date = fields.Date()
    end_date = fields.Date()
    total_price = fields.Float()

class RentalPreviewList_OutputSchema(Schema):
    previews = fields.List(fields.Nested(RentalPreview))
