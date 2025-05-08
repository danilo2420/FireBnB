from marshmallow import Schema, fields, validates, ValidationError, validates_schema


class FavoriteListGet_InputSchema(Schema):
    id = fields.Int()
    user_id = fields.Int()

    @validates_schema
    def validate_data(self, data, **kwargs):
        print(data)
        if len(data) != 1:
            raise ValidationError('you must input one (and only one) query argument')

class FavoriteListCreate_InputSchema(Schema):
    user_id = fields.Int(required=True)
    name = fields.Str()

class LinkPlace_InputSchema(Schema):
    id = fields.Int(required=True) # favorite list's id
    place_id = fields.Int(required=True)

class FavoriteListUpdate_InputSchema(Schema):
    id = fields.Int(required=True)
    name = fields.Str()

'''
class Delete_InputSchema(Schema):
    id = fields.Int()
    guest_id = fields.Int()
    place_id = fields.Int()

    @validates_schema
    def validate_data(self, data, **kwargs):
        if len(data) != 1:
            raise ValidationError('you have to input 1 (and only 1) query argument')
'''