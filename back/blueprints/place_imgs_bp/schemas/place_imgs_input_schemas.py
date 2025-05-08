from marshmallow import Schema, fields, ValidationError, validates_schema

class PlaceImgGet_InputSchema(Schema):
    id = fields.Int()
    place_id = fields.Int()

    @validates_schema
    def validate_schema(self, data, **kwargs):
        if len(data) > 1:
            raise ValidationError('You cannot input more than one argument')

class PlaceImgCreate_InputSchema(Schema):
    place_id = fields.Int(required=True)
    title = fields.Str()
    img = fields.Str() # Should be required=True, probably

class PlaceImgUpdate_InputSchema(Schema):
    id = fields.Int(required=True)
    title = fields.Str()
    img = fields.Str() # Should be required=True, probably

class PlaceImgDelete_InputSchema(Schema):
    id = fields.Int()
    place_id = fields.Int()

    @validates_schema
    def validate_schema(self, data, **kwargs):
        if len(data) != 1:
            raise ValidationError('You have to input one (and only one) argument')