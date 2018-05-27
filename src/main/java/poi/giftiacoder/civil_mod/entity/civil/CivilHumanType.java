package poi.giftiacoder.civil_mod.entity.civil;

public enum CivilHumanType {
	TRAVELLER {
		@Override
		public void updateAI(EntityHumanCivil entity) {
			// TODO Auto-generated method stub
		}
	};
	//TRAVELLER, 
	//KING, 
	//KNIGHT, 
	//SOLDIER, 
	//PRIEST, 
	//CIVILIAN;
	
	public abstract void updateAI(EntityHumanCivil entity);
}
