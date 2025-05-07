from marshmallow import Schema, fields, validates_schema, ValidationError

class RentingSchema(Schema):
    id = fields.Int()
    guest_id = fields.Int()
    place_id = fields.Int()
    start_date = fields.Date()
    end_date = fields.Date()
    total_price = fields.Float()

    # Do date comparison for validation?