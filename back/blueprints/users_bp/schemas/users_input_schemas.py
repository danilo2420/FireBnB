from marshmallow import Schema, fields

class Get_InputSchema(Schema):
    id = fields.Int(required=True)