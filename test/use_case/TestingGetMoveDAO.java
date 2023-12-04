package use_case;

import use_case.Get_move.GetMoveDataAccessInterface;

public class TestingGetMoveDAO implements GetMoveDataAccessInterface {
    String moveto;
    public TestingGetMoveDAO (String moveto){
        this.moveto = moveto;
    }
    @Override
    public String getMoveAfter(int moveNo) {
        return this.moveto;
    }
}
