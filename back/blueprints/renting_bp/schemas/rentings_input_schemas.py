from marshmallow import Schema, fields, validates_schema, ValidationError

class Get_InputSchema(Schema):
    id = fields.Int()
    guest = fields.Int() # Guest id to retrieve all the guest's rentals
    place = fields.Int() # Place id to retrieve all the place's rentals

    @validates_schema
    def validate_schema(self, data, **kwargs):
        if len(data) > 1:
            raise ValidationError('Query should not have more than one parameter')