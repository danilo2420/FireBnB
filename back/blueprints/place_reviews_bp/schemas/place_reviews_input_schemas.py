from marshmallow import Schema, fields, validates, ValidationError, validates_schema

class Get_InputSchema(Schema):
    id = fields.Int()
    guest_id = fields.Int()
    place_id = fields.Int()

    @validates_schema
    def validate_data(self, data, **kwargs):
        if len(data) > 1:
            raise ValidationError('you cant input more than 1 query argument')

'''
class Create_InputSchema(Schema):
    owner_id = fields.Int(required=True)
    name = fields.Str()
    type = fields.Str()
    description = fields.Str()
    price_per_night = fields.Float()
    stars = fields.Int()

    @validates('stars')
    def validate_stars(self, data, **kwargs):
        if data < 0 or data > 5:
            raise ValidationError("'stars' have to be an integer between 0 and 1")
        
class Update_InputSchema(Schema):
    id = fields.Int(required=True)
    name = fields.Str()
    type = fields.Str()
    description = fields.Str()
    price_per_night = fields.Float()
    stars = fields.Int()

    @validates('stars')
    def validate_stars(self, data, **kwargs):
        if data < 0 or data > 5:
            raise ValidationError("'stars' have to be an integer between 0 and 1")
'''