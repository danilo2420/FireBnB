from marshmallow import Schema, fields, validates_schema, ValidationError

class Get_InputSchema(Schema):
    id = fields.Int()
    guest = fields.Int() # Guest id to retrieve all the guest's rentals
    place = fields.Int() # Place id to retrieve all the place's rentals

    @validates_schema
    def validate_schema(self, data, **kwargs):
        if len(data) > 1:
            raise ValidationError('Query should not have more than one parameter')
        
class RentingCreate_InputSchema(Schema):
    id = fields.Int()
    guest_id = fields.Int(required=True)
    place_id = fields.Int(required=True)
    start_date = fields.Date()
    end_date = fields.Date()
    total_price = fields.Float()

class RentingUpdate_InputSchema(Schema):
    id = fields.Int(required=True)
    start_date = fields.Date()
    end_date = fields.Date()
    total_price = fields.Float()

class RentingDelete_InputSchema(Schema):
    id = fields.Int()
    guest = fields.Int()
    place = fields.Int()

    @validates_schema
    def validate_schema(self, data, **kwargs):
        if len(data) != 1:
            raise ValidationError('Query should have one (and only one) parameter')