from marshmallow import Schema, fields, validates, ValidationError, validates_schema

class Get_InputSchema(Schema):
    id = fields.Int()
    guest_id = fields.Int()
    place_id = fields.Int()

    @validates_schema
    def validate_data(self, data, **kwargs):
        if len(data) > 1:
            raise ValidationError('you cant input more than 1 query argument')

class PlaceReviewCreate_InputSchema(Schema):
    guest_id = fields.Int(required=True)
    place_id = fields.Int(required=True)
    date = fields.Date()
    description = fields.Str()
    stars = fields.Int()

class PlaceReviewUpdate_InputSchema(Schema):
    id = fields.Int(required=True)
    date = fields.Date()
    description = fields.Str()
    stars = fields.Int()

class PlaceReviewDelete_InputSchema(Schema):
    id = fields.Int()
    guest_id = fields.Int()
    place_id = fields.Int()

    @validates_schema
    def validate_data(self, data, **kwargs):
        if len(data) != 1:
            raise ValidationError('you have to input 1 (and only 1) query argument')
