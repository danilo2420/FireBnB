from marshmallow import Schema, fields

class Id_InputSchema(Schema):
    id = fields.Int(required=True)

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