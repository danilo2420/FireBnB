from marshmallow import Schema, fields, validates_schema, ValidationError
from model.schemas.rentingSchema import RentingSchema

class RentingGet_OutputSchema(Schema):
    rentings = fields.List(fields.Nested(RentingSchema))