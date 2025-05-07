from sqlalchemy import Column, Integer, String, Boolean, Date, Float, ForeignKey
from sqlalchemy.orm import relationship
from model.model import Base
from model.users import User
from model.places import Place

class Renting(Base):
    __tablename__ = 'rentings'

    id = Column(Integer, primary_key=True)
    guest_id = Column(Integer, ForeignKey('users.id'))
    place_id = Column(Integer, ForeignKey('places.id'))
    start_date = Column(Date)
    end_date = Column(Date)
    total_price = Column(Float)

    guest = relationship('User', back_populates='rentings')
    place = relationship('Place', back_populates='rentings')