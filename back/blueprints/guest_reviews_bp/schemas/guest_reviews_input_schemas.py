from marshmallow import Schema, fields, validates_schema, ValidationError

class Id_InputSchema(Schema):
    id = fields.Int(required=True)

class Get_InputSchema(Schema):
    id = fields.Int()
    host_id = fields.Int()
    guest_id = fields.Int()

    @validates_schema
    def validate_schema(self, data, **kwargs):
        if len(data) > 1:
            raise ValidationError('You cannot input more than one query argument')

class Create_InputSchema(Schema):
    host_id = fields.Int(required=True)
    guest_id = fields.Int(required=True)
    date = fields.Date()
    stars = fields.Int()
    description = fields.Str()

class Update_InputSchema(Schema):
    id = fields.Int(required=True)
    date = fields.Date()
    stars = fields.Int()
    description = fields.Str()