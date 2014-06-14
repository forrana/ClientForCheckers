package com.checkers.controllers;

import com.badlogic.gdx.math.Vector2;
import com.checkers.model.Board;
import com.checkers.model.Cell;
import com.checkers.model.Checker;
import com.checkers.network.client.NetworkClient;

//TODO It's class only for validation, all another code, methods and functions shell be in other classes

public class MoveValidator {
	public static boolean isPlayer0 =true;//manual user switcher
	private static String currStep = new String();
    private static int  fightsBefore = 0;
	private String		currMark;
	private Board		board;
	private NetworkClient network = new NetworkClient();
	private Checker		tmpChecker;
	private Cell		startCell;
	private Cell		targetCell;

	private boolean		isStartMove = false;
	private boolean		isEndMove 	= false;
	public	boolean		isBlackTurn = false;
	private boolean		isCanFight 	= true;
    private boolean     isNetFight  = false;
	private boolean     isFightBegun = false;
    private static Checker fightingChecker = null;

    private static float     BOARD_BOTTOM = 0.5f;
	private enum Direction {TWOLEFTUP, TWOLEFTDOWN,  TWORIGHTUP, TWORIGHTDOWN, LEFTUP, RIGHTUP, LEFTDOWN, RIGHTDOWN, NOTHING};
	private Direction dir = Direction.NOTHING;
	public Checker 	selectedChecker;
	
//constructor	
	/**
	 * Class constructor
	 * @param inpBoard  input board whith cells and checkers
	 */
	public MoveValidator(Board inpBoard){
        System.out.println("game="+NetworkClient.gameH.game.getGauid());
        if(NetworkClient.gameH.game.getWhite().getUuid() ==
                NetworkClient.userH.curUser.getUuid())MoveValidator.isPlayer0 = true;
            else   MoveValidator.isPlayer0 = false;
		this.board = inpBoard;
		BOARD_BOTTOM = inpBoard.getBoardBottom();
	}
	
//erasers	
	/**
	 * TODO Check Why it happening
     *
     * If Cell or checker have active links
	 * program have some problems with work
	 * This function clean all links before elements checked
     *
	 */
	public void clearCells(){		
		startCell = null;
		targetCell = null;
		selectedChecker = null;
        tmpChecker = null;
	}
//getters
	public String getCurrMark(){
		return currMark;
	}
	public boolean getIsStartMove(){
		return isStartMove;
	}
	public boolean getIsEndMove(){
		return isEndMove;
	}
    public static boolean isFightBegun(){
        return (fightsBefore > 0) ? true : false;
    }

    public static Checker getFightinChecker(){
        return fightingChecker;
    }


    //setters
	public void setCurrMark(String inpMark){
		currMark = inpMark;
	}
	
	public void setIsStartMove(){
		isStartMove = !isStartMove;
	}
	

	public void setIsEndMove(){
		isEndMove = !isEndMove;
	}
	
	public void setNetSelectedChecker(float iX, float iY){		
		selectedChecker = board.getCheckerByGLCoord(iX, iY);
		startCell 		= board.getCellByGLCoord(iX, iY);
        isNetFight  = true;
		setIsStartMove();
	}
	
	public void setNetTargetCell(float iX, float iY){
		targetCell = board.getCellByGLCoord(iX, iY);
        isNetFight  = true;
		setIsStartMove();
		setIsEndMove();
	}
	
	public void setSelectedChecker(float iX, float iY){
        isNetFight  = false;
		selectedChecker = board.getCheckerByDeskCoord(iX, iY);
		startCell 		= board.getCellByDeskCoord(iX, iY);
        System.out.println("selectedChecker:"+selectedChecker.getPosition());
		setIsStartMove();
	}
	
	public void setTargetCell(float iX, float iY){
        isNetFight  = false;
		if((targetCell = board.getCellByDeskCoord(iX, iY)) != null)
		setIsStartMove();
		setIsEndMove();
	}
	
	public boolean blackOrWhite(){
		
		if(selectedChecker != null && targetCell != null && targetCell.getColor()){
			
			return true;
		}
		return false;
		
	}
	
	public boolean cellIsBeasy(){
		
		if(board.getCheckerByDeskCoord(targetCell.getPosition())!= null)
			return true;
		
		return false;
	}
	
	public boolean oneCell(){
		float a,b;
		if(blackOrWhite()){
			a = startCell.getPosition().x - targetCell.getPosition().x;
			b = startCell.getPosition().y - targetCell.getPosition().y;
			a = Math.abs(a);
			b = Math.abs(b);
			if(a <= 1 && b <= 1)return true;
		}
		return false;
	}
	public boolean isBeasy(){
		if(blackOrWhite()){		
			if(board.getCheckerByGLCoord(targetCell.getPosition().x,targetCell.getPosition().y) != null){
				return true;
			}
		}
		return false;
	}
	
	public boolean isQueenHere(){
		
		for(Checker check : board.getCheckers())
			if(check.getQueen())return true;
		
		return false;
	}

    public static String getCurrentStep(){
        return currStep;
    }

	/**
	 * Check possibility of queen fight in all directions
	 * @param inQueen - selected Queen
	 * @return	true, if Queen can fight and false if can't
	 * 
	 */
	public boolean isQueenCanFightAllDirection(Checker inQueen){
		float tmpX = 0;
		float tmpY = 0;

		if(inQueen != null){

				tmpX =  inQueen.getPosition().x;
				tmpY =  inQueen.getPosition().y;

				while(tmpX < BOARD_BOTTOM+7f && tmpY < BOARD_BOTTOM+7f){
					tmpX++; tmpY++;
                    if(tmpX == startCell.getPosition().x && tmpY == startCell.getPosition().y)
                        break;

					if((tmpChecker = board.getCheckerByGLCoord(tmpX,tmpY)) != null
                                  && tmpChecker.getColor() == inQueen.getColor())
                                break;

					if((tmpChecker = board.getCheckerByGLCoord(tmpX,tmpY)) != null
                            && board.getCheckerByGLCoord(tmpX+1.0f,tmpY+1.0f) == null
                            && board.getCellByGLCoord(tmpX+1.0f,tmpY+1.0f) != null
                            && tmpChecker.getColor() != inQueen.getColor()){

                            if(!tmpChecker.getIsKilled()){
                                System.out.println(tmpChecker.getPosition());
                                System.out.println(inQueen.getPosition());
							    return true;
                            }
					}else 	if((tmpChecker = board.getCheckerByGLCoord(tmpX,
							tmpY)) != null && board.getCheckerByGLCoord(tmpX+1.0f,
									tmpY+1.0f) != null && board.getCellByGLCoord(tmpX+1.0f,
											tmpY+1.0f) != null)break;	
				}

            tmpX =  inQueen.getPosition().x;
            tmpY =  inQueen.getPosition().y;

				while(tmpX < BOARD_BOTTOM+7f && tmpY > BOARD_BOTTOM){
						tmpX++; tmpY--;
                        if(tmpX == startCell.getPosition().x && tmpY == startCell.getPosition().y)
                                break;

						if((tmpChecker = board.getCheckerByGLCoord(tmpX,tmpY)) != null
                                      && tmpChecker.getColor() == inQueen.getColor())
                                break;
						if((tmpChecker = board.getCheckerByGLCoord(tmpX,tmpY)) != null
                                      && board.getCheckerByGLCoord(tmpX+1.0f,tmpY-1.0f) == null
                                      && board.getCellByGLCoord(tmpX+1.0f,tmpY-1.0f) != null
                                      && tmpChecker.getColor() != inQueen.getColor()){
                            if(!tmpChecker.getIsKilled())
                            {
                                System.out.println("Killed checker:"+tmpChecker.getPosition());
                                System.out.println("Killer queen:"+inQueen.getPosition());

                                return true;
                            }
						}else if((tmpChecker = board.getCheckerByGLCoord(tmpX,tmpY)) != null
                                            && board.getCheckerByGLCoord(tmpX+1.0f,tmpY-1.0f) != null
                                            && board.getCellByGLCoord(tmpX+1.0f,tmpY-1.0f) != null)
                                break;
				}

            tmpX =  inQueen.getPosition().x;
            tmpY =  inQueen.getPosition().y;

				while(tmpX > BOARD_BOTTOM && tmpY > BOARD_BOTTOM){	
					tmpX--; tmpY--;
                    if(tmpX == startCell.getPosition().x && tmpY == startCell.getPosition().y)
                        break;

                    if((tmpChecker = board.getCheckerByGLCoord(tmpX,tmpY)) != null
                            && tmpChecker.getColor() == inQueen.getColor())
                            break;

                    if((tmpChecker = board.getCheckerByGLCoord(tmpX,tmpY)) != null
                            && board.getCheckerByGLCoord(tmpX-1.0f,tmpY-1.0f) == null
                            &&  board.getCellByGLCoord(tmpX-1.0f,tmpY-1.0f) != null
                            && tmpChecker.getColor() != inQueen.getColor()){
                        if(!tmpChecker.getIsKilled()){
                            System.out.println("Killed checker:"+tmpChecker.getPosition());
                            System.out.println("Killer queen:"+inQueen.getPosition());

                            return true;
                        }

					}else if((tmpChecker = board.getCheckerByGLCoord(tmpX,tmpY)) != null
                               && board.getCheckerByGLCoord(tmpX-1.0f,tmpY-1.0f) != null
                               &&  board.getCellByGLCoord(tmpX-1.0f,tmpY-1.0f) != null)break;
			}

            tmpX =  inQueen.getPosition().x;
            tmpY =  inQueen.getPosition().y;

				while(tmpX > BOARD_BOTTOM && tmpY < BOARD_BOTTOM + 7f){
					tmpX--; tmpY++;
                    if(tmpX == startCell.getPosition().x && tmpY == startCell.getPosition().y)
                        break;

                    if((tmpChecker = board.getCheckerByGLCoord(tmpX,tmpY)) != null
                     && tmpChecker.getColor() == inQueen.getColor())
                            break;

                    if((tmpChecker = board.getCheckerByGLCoord(tmpX,tmpY)) != null
                                       && board.getCheckerByGLCoord(tmpX-1.0f,tmpY+1.0f) == null
                                       && board.getCellByGLCoord(tmpX-1.0f,tmpY+1.0f) != null
                                       && tmpChecker.getColor() != inQueen.getColor()){
                        if(!tmpChecker.getIsKilled()){
                            System.out.println("Killed checker-+:"+tmpChecker.getPosition());
                            System.out.println("Killer queen-+:"+inQueen.getPosition());

                            return true;
                        }
					}else	if((tmpChecker = board.getCheckerByGLCoord(tmpX,tmpY)) != null
                                          && board.getCheckerByGLCoord(tmpX-1.0f,tmpY+1.0f) != null
                                          && board.getCellByGLCoord(tmpX-1.0f,tmpY+1.0f) != null)
                            break;
			    }
			}	
		return false;
	}

    public boolean isVirtQueenCanFightAllDirection(Checker inQueen){
        float tmpX = 0;
        float tmpY = 0;

        if(inQueen != null){

            tmpX =  targetCell.getPosition().x;
            tmpY =  targetCell.getPosition().y;

            while(tmpX < BOARD_BOTTOM+7f && tmpY < BOARD_BOTTOM+7f){
                tmpX++; tmpY++;
                if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && tmpChecker.getColor() == inQueen.getColor())break;
                if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && board.getCheckerByGLCoord(tmpX+1.0f,
                        tmpY+1.0f) == null && board.getCellByGLCoord(tmpX+1.0f,
                        tmpY+1.0f) != null){
                    if(!tmpChecker.getIsKilled()){
                        System.out.println(tmpChecker.getPosition());
                        return true;
                    }
                }else 	if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && board.getCheckerByGLCoord(tmpX+1.0f,
                        tmpY+1.0f) != null && board.getCellByGLCoord(tmpX+1.0f,
                        tmpY+1.0f) != null)break;
            }

            tmpX =  targetCell.getPosition().x;
            tmpY =  targetCell.getPosition().y;

            while(tmpX < BOARD_BOTTOM+7f && tmpY > BOARD_BOTTOM){
                tmpX++; tmpY--;
                if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && tmpChecker.getColor() == inQueen.getColor())break;
                if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && board.getCheckerByGLCoord(tmpX+1.0f,
                        tmpY-1.0f) == null && board.getCellByGLCoord(tmpX+1.0f,
                        tmpY-1.0f) != null){
                    if(!tmpChecker.getIsKilled())
                    {
                        System.out.println(tmpChecker.getPosition());
                        return true;
                    }
                }else if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && board.getCheckerByGLCoord(tmpX+1.0f,
                        tmpY-1.0f) != null && board.getCellByGLCoord(tmpX+1.0f,
                        tmpY-1.0f) != null) break;
            }

            tmpX =  targetCell.getPosition().x;
            tmpY =  targetCell.getPosition().y;

            while(tmpX > BOARD_BOTTOM && tmpY > BOARD_BOTTOM){
                tmpX--; tmpY--;
                if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && tmpChecker.getColor() == inQueen.getColor())break;
                if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && board.getCheckerByGLCoord(tmpX-1.0f,
                        tmpY-1.0f) == null &&  board.getCellByGLCoord(tmpX-1.0f,
                        tmpY-1.0f) != null){
                    if(!tmpChecker.getIsKilled()){

                        System.out.println(tmpChecker.getPosition());
                        return true;

                    }

                }else if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && board.getCheckerByGLCoord(tmpX-1.0f,
                        tmpY-1.0f) != null &&  board.getCellByGLCoord(tmpX-1.0f,
                        tmpY-1.0f) != null)break;
            }

            tmpX =  targetCell.getPosition().x;
            tmpY =  targetCell.getPosition().y;

            while(tmpX > BOARD_BOTTOM && tmpY < BOARD_BOTTOM + 7f){
                tmpX--; tmpY++;
                if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && tmpChecker.getColor() == inQueen.getColor())break;
                if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && board.getCheckerByGLCoord(tmpX-1.0f,
                        tmpY+1.0f) == null && board.getCellByGLCoord(tmpX-1.0f,
                        tmpY+1.0f) != null){
                    if(!tmpChecker.getIsKilled()){
                        System.out.println(tmpChecker.getPosition());
                        return true;
                    }
                }else	if((tmpChecker = board.getCheckerByGLCoord(tmpX,
                        tmpY)) != null && board.getCheckerByGLCoord(tmpX-1.0f,
                        tmpY+1.0f) != null && board.getCellByGLCoord(tmpX-1.0f,
                        tmpY+1.0f) != null)break;
            }
        }
        return false;
    }


    /**
	 * check possibility all queen's of fight in current direction
	 * @param inQueen - selected Queen
	 * @return	true, if Queen can fight and false if can't
	 * 
	 */
	public boolean isQueenCanFight(Checker inQueen){
		float a,b;
		float tmpX = 0;
		float tmpY = 0;
        int   killedCount = 0;
		boolean returnFlag = false;
		
		if(!blackOrWhite())return false;
		
		if(inQueen != null){		
			
			a = startCell.getPosition().x - targetCell.getPosition().x;
			b = startCell.getPosition().y - targetCell.getPosition().y;
			
			tmpX =  startCell.getPosition().x;
			tmpY =  startCell.getPosition().y;	
			
			if(a < 0 && b < 0){
				while(tmpX < targetCell.getPosition().x && tmpY < targetCell.getPosition().y){		
					tmpX++; tmpY++;
					if((tmpChecker = board.getCheckerByGLCoord(tmpX,
							tmpY)) != null && tmpChecker.getColor() == inQueen.getColor()){returnFlag = false;break;}
					
					if((tmpChecker = board.getCheckerByGLCoord(tmpX,
						tmpY)) != null && board.getCheckerByGLCoord(tmpX+1.0f,
								tmpY+1.0f) == null && board.getCellByGLCoord(tmpX+1.0f,
										tmpY+1.0f) != null){
                           if(!tmpChecker.getIsKilled()){
							tmpChecker.setIsKilled();
							returnFlag = true;
                            killedCount++;
							continue;
                           }
							
					}else 	if((tmpChecker = board.getCheckerByGLCoord(tmpX,
							tmpY)) != null && board.getCheckerByGLCoord(tmpX+1.0f,
									tmpY+1.0f) != null && board.getCellByGLCoord(tmpX+1.0f,
											tmpY+1.0f) != null){returnFlag = false;break;}
				}		
			}else
			if(a < 0 && b > 0){
					while(tmpX < targetCell.getPosition().x && tmpY > targetCell.getPosition().y){		
						tmpX++; tmpY--;
						if((tmpChecker = board.getCheckerByGLCoord(tmpX,
								tmpY)) != null && tmpChecker.getColor() == inQueen.getColor()){returnFlag = false;break;}
						if((tmpChecker = board.getCheckerByGLCoord(tmpX,
							tmpY)) != null && board.getCheckerByGLCoord(tmpX+1.0f,
									tmpY-1.0f) == null && board.getCellByGLCoord(tmpX+1.0f,
											tmpY-1.0f) != null){
                            if(!tmpChecker.getIsKilled()){
                                tmpChecker.setIsKilled();
								returnFlag = true;
                                killedCount++;
								continue;
                            }

						}else if((tmpChecker = board.getCheckerByGLCoord(tmpX,
								tmpY)) != null && board.getCheckerByGLCoord(tmpX+1.0f,
										tmpY-1.0f) != null && board.getCellByGLCoord(tmpX+1.0f,
												tmpY-1.0f) != null){returnFlag = false;break;}	
				}	
			}else		
			if(a > 0 && b > 0){		
				while(tmpX > targetCell.getPosition().x && tmpY > targetCell.getPosition().y){	
					tmpX--; tmpY--;
					if((tmpChecker = board.getCheckerByGLCoord(tmpX,
							tmpY)) != null && tmpChecker.getColor() == inQueen.getColor()){returnFlag = false;break;}
					if((tmpChecker = board.getCheckerByGLCoord(tmpX,
						tmpY)) != null && board.getCheckerByGLCoord(tmpX-1.0f,
								tmpY-1.0f) == null &&  board.getCellByGLCoord(tmpX-1.0f,
										tmpY-1.0f) != null){
                        if(!tmpChecker.getIsKilled()){
                            tmpChecker.setIsKilled();
							returnFlag = true;
                            killedCount++;
							continue;
                        }
					}else if((tmpChecker = board.getCheckerByGLCoord(tmpX,
							tmpY)) != null && board.getCheckerByGLCoord(tmpX-1.0f,
									tmpY-1.0f) != null &&  board.getCellByGLCoord(tmpX-1.0f,
											tmpY-1.0f) != null){returnFlag = false;break;}
			}
			}else				
			if(a > 0 && b < 0){				
				while(tmpX > targetCell.getPosition().x && tmpY < targetCell.getPosition().y){
					tmpX--; tmpY++;
					if((tmpChecker = board.getCheckerByGLCoord(tmpX,
							tmpY)) != null && tmpChecker.getColor() == inQueen.getColor()){returnFlag = false;break;}
					if((tmpChecker = board.getCheckerByGLCoord(tmpX,
						tmpY)) != null && board.getCheckerByGLCoord(tmpX-1.0f,
								tmpY+1.0f) == null && board.getCellByGLCoord(tmpX-1.0f,
										tmpY+1.0f) != null){
                        if(!tmpChecker.getIsKilled()){
                            tmpChecker.setIsKilled();
						   	returnFlag = true;
                            killedCount++;
							continue;
                        }
					}else	if((tmpChecker = board.getCheckerByGLCoord(tmpX,
							tmpY)) != null && board.getCheckerByGLCoord(tmpX-1.0f,
									tmpY+1.0f) != null && board.getCellByGLCoord(tmpX-1.0f,
											tmpY+1.0f) != null){returnFlag = false;break;}
			}
			}	
		}

        if(killedCount > 1){
            for(Checker checker : board.getCheckers())
                if(checker.getIsKilled()){
                    checker.clearIsKilled();
                    System.out.println("cleared");
                }
            System.out.println("count "+killedCount);
        }
        System.out.println("killedC"+killedCount);
        System.out.println("flag"+returnFlag);
		    return returnFlag;

	}

	public boolean isLineMovement(){
		float a,b;	
		a = startCell.getPosition().x - targetCell.getPosition().x;
		b = startCell.getPosition().y - targetCell.getPosition().y;
		
		if(Math.abs(a)!= Math.abs(b)){return false;}
			else return true;
	}
	//set queen direction
	public void setDirection(){
		float a,b;
		
		a = startCell.getPosition().x - targetCell.getPosition().x;
		b = startCell.getPosition().y - targetCell.getPosition().y;
	
		if(Math.abs(a)!= Math.abs(b))dir = Direction.NOTHING;
			else{
				if(a < 0 && b < 0) dir = Direction.RIGHTUP;
				if(a >  0 && b < 0) dir = Direction.LEFTUP;
				if(a >  0 && b > 0)  dir = Direction.LEFTDOWN;
				if(a <  0 && b > 0) dir = Direction.RIGHTDOWN;
			}
		
	}
	// set direction and type of move
	public boolean twoCell(){
		float a,b;
		if(blackOrWhite()){
			a = startCell.getPosition().x - targetCell.getPosition().x;
			b = startCell.getPosition().y - targetCell.getPosition().y;
			if(a == -1 && b == -1) dir = Direction.RIGHTUP;
			if(a ==  1 && b == -1) dir = Direction.LEFTUP;
			if(a ==  1 && b == 1)  dir = Direction.LEFTDOWN;
			if(a ==  -1 && b == 1) dir = Direction.RIGHTDOWN;

			if(a == -2 && b == -2){
				dir = Direction.TWORIGHTUP;
				if((tmpChecker = board.getCheckerByGLCoord(startCell.getPosition().x+1.0f,
						startCell.getPosition().y+1.0f)) != null){
                            tmpChecker.setIsKilled();
							return true;
				}				
			}else
			if(a == -2 && b == 2){
				dir = Direction.TWORIGHTDOWN;
				if((tmpChecker = board.getCheckerByGLCoord(startCell.getPosition().x+1.0f,
						startCell.getPosition().y-1.0f)) != null){
                            tmpChecker.setIsKilled();
                            return true;
				}		
			}else
			if(a == 2 && b == 2){
				dir = Direction.TWOLEFTDOWN;
				if((tmpChecker = board.getCheckerByGLCoord(startCell.getPosition().x-1.0f,
						startCell.getPosition().y-1.0f)) != null){
                            tmpChecker.setIsKilled();
							return true;
				}
			}else
                if(a == 2 && b == -2){
                    dir = Direction.TWOLEFTUP;
                    if((tmpChecker = board.getCheckerByGLCoord(startCell.getPosition().x-1.0f,
                            startCell.getPosition().y+1.0f)) != null){
                                tmpChecker.setIsKilled();
                                return true;
                    }
                }
		}
		
		return false;
	}
	
	public void moveChecker(float screenX, float screenY){
		float tX;
		float tY;
		tX = (screenX / board.ppuX) - BOARD_BOTTOM;
		tY = (screenY / board.ppuY) + BOARD_BOTTOM;
		
		tY = tY - (8.0f + BOARD_BOTTOM);
		tY = Math.abs(tY);
		
		if(selectedChecker != null)
			selectedChecker.setPosition(new Vector2(tX, tY));
		
	}
	
	public boolean moveOneCell(){
		if(oneCell() && !isBeasy()){
			checkQueen();
			return true;
		}
		return false;
	}

	public boolean checkColor(){
		if( tmpChecker.getColor() != selectedChecker.getColor())return true;
		else tmpChecker = null;
		return false;
	}
	//do kill another checker
	public boolean moveTwoCell(){
		
		if(twoCell() && !isBeasy() && tmpChecker != null && checkColor() && targetCell != null){
                System.out.println("isKilled:"+tmpChecker.getIsKilled());
				selectedChecker.setPosition(targetCell.getPosition());
                checkQueen();

				return true;
		}	
		return false;
	}
	
	//returning checker on start position
	public String returnChecker(){
		if(selectedChecker != null) selectedChecker.setPosition(startCell.getPosition());
		return "0";
	}
	
	public void setNetwork(NetworkClient net){
		network = net;
	}
	
	//do checker fight or return it
	private String twoSteps(){
        String result = "0";
		 if(fightsBefore == 0){
            if(!moveTwoCell()){
                result = returnChecker();
                return result;
            }

            //if(checkMove(isBlackTurn)){
             if(isCheckerCanFight(selectedChecker)){
                if(selectedChecker.getQueen()){
                        if(isVirtQueenCanFightAllDirection(selectedChecker) == false){
                            System.out.println("Queen Kild!");
                            System.out.println("Qresult:4");
                            isCanFight = true;
                            return "4";
                        }else{
                            fightingChecker = selectedChecker;
                            System.out.println("Qresult:5");
                            fightsBefore++;
                            return "5";
                        }

                }
                if(isBlackTurn != isPlayer0){
                    result = "4";
                }else result = "2";
                    isCanFight = true;
                    selectedChecker = null;
                return result;
            }else {
                fightingChecker = selectedChecker;
                result = "5";
                fightsBefore++;
            }
         }
        else {
             if(!moveTwoCell()){
                 result = returnChecker();
                 return result;
             }
             else if(isCheckerCanFight(selectedChecker)){
                 if(selectedChecker.getQueen()){
                     if(isVirtQueenCanFightAllDirection(selectedChecker)){
                         fightsBefore++;
                         fightingChecker = selectedChecker;
                         return "6";
                     }else{
                         isCanFight = true;
                         fightsBefore = 0;
                         System.out.println("Qresult:7"+isCanFight);
                         fightingChecker = null;
                         return "7";
                     }
                 }
                 if(isBlackTurn != isPlayer0){
                     result = "7";
                 }else result = "2";

                 fightingChecker = null;
                 isCanFight = true;
                 selectedChecker = null;
                 fightsBefore = 0;
                 return result;
             }else result = "6";
         }

      return result;
	}

	//prepare date and stats before new turn

    //TODO fix this bag
	private void endCheckerMove(){
		dir = Direction.NOTHING;
		clearCells();
        if(!checkQueensMove(isBlackTurn))isCanFight = false;
		if(!checkMove(isBlackTurn))isCanFight = false;
	}
	
	//if checker arrived on end of board - transform it in queen
	private boolean checkQueen(){
        System.out.println("check queen:" + selectedChecker.getPosition().y);
		if(selectedChecker != null && !selectedChecker.getQueen()){
			if(selectedChecker.getColor()){
				if(selectedChecker.getPosition().y == BOARD_BOTTOM){
							selectedChecker.setQueen();
							return true;
				}
			}else{
				if(selectedChecker.getPosition().y == (7f+BOARD_BOTTOM)){
					        selectedChecker.setQueen();
					        return true;
				}
			}	
		}
		return false;
	}
	
	private String moveToStr(float x, float y){
		if(currStep.length() != 0)currStep += getCurrMark();
				currStep += (char)((int)(x - BOARD_BOTTOM) + (int)'a');
				currStep += Integer.toString((int)(y+BOARD_BOTTOM));

		return currStep;
	}
	
	public Vector2 strToMove(String move){
		Vector2 coord = new Vector2();
		String sX, sY;
		sY = move.substring(1);
				coord.x = (int)move.toCharArray()[0] - (int)'a' + board.getBoardBottom();
				coord.y = Integer.parseInt(sY) - board.getBoardBottom();
		return coord;
	}
	
	//Do checker move, or return if unpossible
	private String oneStep(boolean bool){
        String isSucsess = "0";
		if(isCanFight){
			if(!moveOneCell() || bool)isSucsess = returnChecker();
				else {
                    if(isBlackTurn != isPlayer0){
                        isSucsess = "3";
                    }else isSucsess = "2";
				}
		}
		else {
            returnChecker();
            isSucsess = "1";
        }
		dir = Direction.NOTHING;
		clearCells();
		if(!checkMove(isBlackTurn)){
            isCanFight = false;
        }
        return isSucsess;
	}
	
	//If friend queen or checker on patch - return true
	private boolean isFriendOnLine(){
		
		float tmpX, tmpY;
		float maxTmpX, maxTmpY;
		float a, b;
		
		tmpX = startCell.getPosition().x;
		tmpY = startCell.getPosition().y;
		maxTmpX = targetCell.getPosition().x;
		maxTmpY = targetCell.getPosition().y;
	
		a = tmpX - maxTmpX;
		b = tmpY - maxTmpY;
			
		if(a > 0 && b < 0){ 		
			while(tmpX > maxTmpX && tmpY < maxTmpY){	
				tmpX--; 
				tmpY++;
				if(board.getCheckerByGLCoord(tmpX, tmpY) != null && board.getCheckerByGLCoord(tmpX, tmpY).getColor() == selectedChecker.getColor())
					return true;
			}
		}
		if(a < 0 && b < 0){
			while(tmpX < maxTmpX && tmpY < maxTmpY){	
				tmpX++; 
				tmpY++;
				if(board.getCheckerByGLCoord(tmpX, tmpY) != null && board.getCheckerByGLCoord(tmpX, tmpY).getColor() == selectedChecker.getColor())
					return true;
			}	
		}
		if(a < 0 && b > 0){
			while(tmpX < maxTmpX && tmpY > maxTmpY){	
				tmpX++; 
				tmpY--;
				if(board.getCheckerByGLCoord(tmpX, tmpY) != null && board.getCheckerByGLCoord(tmpX, tmpY).getColor() == selectedChecker.getColor())
					return true;
			}						
		}
		if(a >  0 && b > 0){ 
			while(tmpX > maxTmpX && tmpY > maxTmpY){	
				tmpX--; 
				tmpY--;
				if(board.getCheckerByGLCoord(tmpX, tmpY) != null && board.getCheckerByGLCoord(tmpX, tmpY).getColor() == selectedChecker.getColor())
					return true;
			}	
		}
		return false;
	}

	//this function return false if something here on queen patch
	private boolean isQueenPatchFree(){
		if(!isCanFight) return false;
		if(!blackOrWhite())return false;
		
		float tmpX, tmpY;
		float maxTmpX, maxTmpY;
		float a, b;
	
		tmpX = startCell.getPosition().x;
		tmpY = startCell.getPosition().y;
		maxTmpX = targetCell.getPosition().x;
		maxTmpY = targetCell.getPosition().y;
	
		a = tmpX - maxTmpX;
		b = tmpY - maxTmpY;
		
		if(a == 0 && b == 0)return false;
		
		if(a > 0 && b < 0){ 		
			while(tmpX > maxTmpX && tmpY < maxTmpY){	
				tmpX--; 
				tmpY++;
				if(board.getCheckerByGLCoord(tmpX, tmpY) != null)
					return false;
			}
		}
		if(a < 0 && b < 0){
			while(tmpX < maxTmpX && tmpY < maxTmpY){	
				tmpX++; 
				tmpY++;
				if(board.getCheckerByGLCoord(tmpX, tmpY) != null)
					return false;
			}	
		}
		if(a < 0 && b > 0){
			while(tmpX < maxTmpX && tmpY > maxTmpY){	
				tmpX++; 
				tmpY--;
				if(board.getCheckerByGLCoord(tmpX, tmpY) != null)
					return false;
			}						
		}
		if(a >  0 && b > 0){ 
			while(tmpX > maxTmpX && tmpY > maxTmpY){	
				tmpX--; 
				tmpY--;
				if(board.getCheckerByGLCoord(tmpX, tmpY) != null)
					return false;
			}	
		}
		return true;
	}
	//This function return false if some queen can fight
	public boolean checkQueensMove(boolean bool){
		//for(Checker forChecker : board.getCheckers()){
        Checker forChecker;
        for(int i=0; i < board.getCheckers().size;i++){
            forChecker = board.getCheckers().get(i);
			if(forChecker.getColor() == bool && forChecker.getQueen()){			
				if(isQueenCanFightAllDirection(forChecker)){
                    System.out.println("Queen can fight CQM");
					return false;
				}	
			}
		}	
		return true;
	}
	
	//This function return false if some checker can fight
	public boolean checkMove(boolean bool){
		Checker virtChecker;
		Checker forChecker;
		//work with checker array make conflict 
		//because function getCheckerByCoord() use foreach too and change iterator
		for(Cell forCell : board.getCells()){
            if(forCell.getColor()){
                if((forChecker = board.getCheckerByGLCoord(forCell.getPosition())) != null && !forChecker.getQueen()){
                    if(forChecker.getColor() == bool){
                        if((virtChecker = board.getCheckerByGLCoord(forCell.getPosition().x+1.0f,
                                forCell.getPosition().y+1.0f)) != null){
                            if(virtChecker.getColor() != forChecker.getColor() && !virtChecker.getIsKilled())
                                if(board.getCheckerByGLCoord(forCell.getPosition().x+2.0f, forCell.getPosition().y+2.0f) == null)
                                        if(forCell.getPosition().x+2 < 8  && forCell.getPosition().y+2 < 8){
                                            forChecker.setCanFight();
                                            return false;
                                        }
                    }
                        if((virtChecker = board.getCheckerByGLCoord(forCell.getPosition().x-1.0f,
                                forCell.getPosition().y-1.0f)) != null){
                            if(virtChecker.getColor() != forChecker.getColor() && !virtChecker.getIsKilled())
                                if(board.getCheckerByGLCoord(forCell.getPosition().x-2.0f,
                                        forCell.getPosition().y-2.0f) == null)
                                        if(forCell.getPosition().x-2 >= 0  && forCell.getPosition().y-2 >= 0){
                                            forChecker.setCanFight();
                                            return false;
                                        }
                    }
                        if((virtChecker = board.getCheckerByGLCoord(forCell.getPosition().x+1.0f,
                                forCell.getPosition().y-1.0f)) != null){
                            if(virtChecker.getColor() != forChecker.getColor() && !virtChecker.getIsKilled())
                                if(board.getCheckerByGLCoord(forCell.getPosition().x+2.0f,
                                        forCell.getPosition().y-2.0f) == null)
                                        if(forCell.getPosition().x+2 < 8  && forCell.getPosition().y-2 >= 0){
                                            forChecker.setCanFight();
                                            return false;
                                        }
                    }
                        if((virtChecker = board.getCheckerByGLCoord(forCell.getPosition().x-1.0f,
                                forCell.getPosition().y+1.0f)) != null){
                            if(virtChecker.getColor() != forChecker.getColor() && !virtChecker.getIsKilled())
                                if(board.getCheckerByGLCoord(forCell.getPosition().x-2.0f,
                                        forCell.getPosition().y+2.0f) == null)
                                        if(forCell.getPosition().x-2 >= 0  && forCell.getPosition().y+2 < 8){
                                            forChecker.setCanFight();
                                            return false;
                                        }
                        }
                    }
                }
            }
		}
		return true;
	}
	//Check selected checker can fight
    public boolean isCheckerCanFight(Checker checker){
        Checker virtChecker;
        Checker forChecker;
        Cell    forCell;
        //work with checker array make conflict
        //because function getCheckerByCoord() use foreach too and change iterator
                forChecker = checker;
                forCell = board.getCellByGLCoord(checker.getPosition().x,checker.getPosition().y);

                        if((virtChecker = board.getCheckerByGLCoord(forCell.getPosition().x+1.0f,
                                forCell.getPosition().y+1.0f)) != null){
                            if(virtChecker.getColor() != forChecker.getColor() && !virtChecker.getIsKilled())
                                if(board.getCheckerByGLCoord(forCell.getPosition().x+2.0f, forCell.getPosition().y+2.0f) == null)
                                    if(forCell.getPosition().x+2 < 8  && forCell.getPosition().y+2 < 8){
                                        forChecker.setCanFight();
                                        return false;
                                    }
                        }
                        if((virtChecker = board.getCheckerByGLCoord(forCell.getPosition().x-1.0f,
                                forCell.getPosition().y-1.0f)) != null){
                            if(virtChecker.getColor() != forChecker.getColor() && !virtChecker.getIsKilled())
                                if(board.getCheckerByGLCoord(forCell.getPosition().x-2.0f,
                                        forCell.getPosition().y-2.0f) == null)
                                    if(forCell.getPosition().x-2 >= 0  && forCell.getPosition().y-2 >= 0){
                                        forChecker.setCanFight();
                                        return false;
                                    }
                        }
                        if((virtChecker = board.getCheckerByGLCoord(forCell.getPosition().x+1.0f,
                                forCell.getPosition().y-1.0f)) != null){
                            if(virtChecker.getColor() != forChecker.getColor() && !virtChecker.getIsKilled())
                                if(board.getCheckerByGLCoord(forCell.getPosition().x+2.0f,
                                        forCell.getPosition().y-2.0f) == null)
                                    if(forCell.getPosition().x+2 < 8  && forCell.getPosition().y-2 >= 0){
                                        forChecker.setCanFight();
                                        return false;
                                    }
                        }
                        if((virtChecker = board.getCheckerByGLCoord(forCell.getPosition().x-1.0f,
                                forCell.getPosition().y+1.0f)) != null){
                            if(virtChecker.getColor() != forChecker.getColor() && !virtChecker.getIsKilled())
                                if(board.getCheckerByGLCoord(forCell.getPosition().x-2.0f,
                                        forCell.getPosition().y+2.0f) == null)
                                    if(forCell.getPosition().x-2 >= 0  && forCell.getPosition().y+2 < 8){
                                        forChecker.setCanFight();
                                        return false;
                                    }
                        }



        return true;
    }


	//Do queen return, move or fight
	public String doQueenMove(){
        String result = "0";
		if(!isLineMovement() || isBeasy() || isFriendOnLine()){
                return returnChecker();
        }
		else if(isQueenPatchFree()){
				if(isBlackTurn != isPlayer0){
					result = "3";
				}else result = "2";

                return result;
			}else if(isQueenCanFight(selectedChecker) || isCanFight){
             if(fightsBefore == 0){
				if(isVirtQueenCanFightAllDirection(selectedChecker) == false){
                    System.out.println("Queen Kild!");
                    System.out.println("Qresult:4");
                    isCanFight = true;
                    return "4";
				}else{
                    System.out.println("Qresult:5");
                    fightsBefore++;
                    return "5";
                }
             }else{
                 if(isVirtQueenCanFightAllDirection(selectedChecker)){
                     fightsBefore++;
                     return "6";
                 }else{
                     isCanFight = true;
                     fightsBefore = 0;
                     fightingChecker = null;
                     System.out.println("Qresult:7"+isCanFight);
                     return "7";
                 }
             }
			}else result = returnChecker();
        return result;
	}

	/**this function prepare date and stats before new turn
     **/
    public void endQueenMove(){
		dir = Direction.NOTHING;
		clearCells();
		if(!checkMove(isBlackTurn)){
            System.out.println("Checker can fight CQM");
            isCanFight = false;
        }
		if(!checkQueensMove(isBlackTurn)){
            isCanFight = false;
        }
	}

    //TODO change status - 000-099 - rejects, 100-199 - success moves, 200-299 - success fights
	//This function do main work of check move.
	  /*
	   *code 0 - turn disallowed - without reason
	   *code 1 - turn disallowed - smb can fight
	   *code 2 - turn allowed - virtual turn - send to server disabled
	   *code 3 - turn allowed - real turn - send to server enabled
	   *code 4 - turn allowed - real turn - single kill
	   *code 5 - turn allowed - real turn - but u can kill another one
	   *code 6 - turn allowed - real turn - next kill from multi kill
	   *code 7 - turn allowed - real turn - last kill from multi kill
	   */
    public String doCheckMove(){
        String isSucsess = "0";
		if(selectedChecker != null){			
			if(selectedChecker.getColor() == isBlackTurn){	
				if(isQueenHere())if(!checkQueensMove(isBlackTurn)){isCanFight = false;}//It's needed for check new queen's
				if(!selectedChecker.getQueen()){	//If current checker not queen do this	
						twoCell();
                        String tmpResult;
						switch(dir){
							case TWOLEFTUP 		:
							case TWOLEFTDOWN 	:
							case TWORIGHTUP 	:
							case TWORIGHTDOWN 	: isSucsess=((tmpResult=twoSteps()) != "0")? tmpResult : "0";break;
							case LEFTUP			:
							case RIGHTUP		: isSucsess=((tmpResult = oneStep(selectedChecker.getColor())) != "0") ? tmpResult : "0";break;
							case RIGHTDOWN		:
							case LEFTDOWN		: isSucsess=((tmpResult = oneStep(!selectedChecker.getColor())) != "0") ? tmpResult : "0";break;
							case NOTHING 		: returnChecker();break;			
						}	
				}else{
                        String tmpResult;
						setDirection();
						switch(dir){
							case LEFTUP			:
							case RIGHTUP		:
							case RIGHTDOWN		:
							case LEFTDOWN		: isSucsess = doQueenMove();break;
							case NOTHING 		: returnChecker();break;		
						}	
				}	
			}else returnChecker();
		}
      //  endCheckerMove();
	  // }else returnChecker();
        System.out.println("killFlag:"+isCanFight);
        System.out.println("Sucsess code:"+isSucsess);
	return isSucsess;
	}
}//end of class
