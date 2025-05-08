from marshmallow import Schema, fields, validates_schema, ValidationError

class GuestReviewGet_InputSchema(Schema):
    id = fields.Int()
    host_id = fields.Int()
    guest_id = fields.Int()

    @validates_schema
    def validate_schema(self, data, **kwargs):
        if len(data) > 1:
            raise ValidationError('You cannot input more than one query argument')

class GuestReviewCreate_InputSchema(Schema):
    host_id = fields.Int(required=True)
    guest_id = fields.Int(required=True)
    date = fields.Date()
    stars = fields.Int()
    description = fields.Str()

class GuestReviewUpdate_InputSchema(Schema):
    id = fields.Int(required=True)
    date = fields.Date()
    stars = fields.Int()
    description = fields.Str()

# This is the same as the Get one, but you can't choose to delete all
class GuestReviewDelete_InputSchema(Schema):
    id = fields.Int()
    host_id = fields.Int()
    guest_id = fields.Int()

    @validates_schema
    def validate_schema(self, data, **kwargs):
        if len(data) != 1:
            raise ValidationError('You need to input one (and only one) argument')