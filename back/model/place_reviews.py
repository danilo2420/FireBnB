from sqlalchemy import Column, String, Integer, ForeignKey, Date
from sqlalchemy.orm import relationship
from model.model import Base

class PlaceReview(Base):
    __tablename__ = 'place_reviews'

    id = Column(Integer, primary_key=True)
    guest_id = Column(Integer, ForeignKey('users.id'))
    place_id = Column(Integer, ForeignKey('places.id'))
    date = Column(Date)
    description = Column(String)
    stars = Column(Integer)

    guest = relationship('User', back_populates='place_reviews')
    place = relationship('Place', back_populates='reviews')
