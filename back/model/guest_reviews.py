from sqlalchemy import Column, Integer, String, ForeignKey, Date
from sqlalchemy.orm import relationship
from model.model import Base

class GuestReview(Base):
    __tablename__ = "guest_reviews"

    # attributes
    id = Column(Integer, primary_key=True)
    host_id = Column(Integer, ForeignKey('users.id'))
    guest_id = Column(Integer, ForeignKey('users.id'))
    date = Column(Date)
    stars = Column(Integer)
    description = Column(String)
    
    # Relationships
    host_user = relationship('User', foreign_keys=[host_id], back_populates="host_reviews")
    guest_user = relationship('User', foreign_keys=[guest_id], back_populates="guest_reviews")