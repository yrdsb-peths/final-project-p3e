
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class survivorIdleKnife here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class startingSurvivor extends Actor
{
    public static int survivorX, survivorY, wait, knifeWait, health; 
    public double stamina = 100; 
    public int movementSpeed = 5; 
    SimpleTimer timer= new SimpleTimer();
    SimpleTimer timer1= new SimpleTimer();
    SimpleTimer timer2= new SimpleTimer();
    GreenfootImage[] idle = new GreenfootImage[19];
    GreenfootImage[] attack = new GreenfootImage[14];
    public Color lava = new Color(255, 83, 66);
    public Color lava2 = new Color(255, 129, 57);
    public Color lava3 = new Color(255, 110, 68); 
    public Color lightGrass = new Color(47, 129, 54);
    public Color darkGrass = new Color(0, 67, 55);
    public Color hole = new Color(0, 0, 0);  
    public static boolean moving, slowMoving;  
    //
    SimpleTimer infiniteStamina = new SimpleTimer();
    public static boolean infStamina; 
    /**
     * Act - do whatever the survivorIdleKnife wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public startingSurvivor()
    {
        
        knifeWait = 0; 
        health = 100; 

        timer.mark();
        timer1.mark();
        timer2.mark();
          
        for(int i = 0; i < idle.length; i++)
        {
            idle[i] = new GreenfootImage("images/Top_Down_Survivor/knife/move/survivor-move_knife_" + i + ".png");
            idle[i].scale(100,100); 
        }
        
        for(int i = 0; i < attack.length; i++)
        {
            attack[i] = new GreenfootImage("images/Top_Down_Survivor/knife/meleeattack/survivor-meleeattack_knife_" + i + ".png");
            attack[i].scale(125,125); 
        }
        setImage(idle[0]); 
        
        gun.pistolSelected = false; 
        gun.rifleSelected = false; 
        gun.shotgunSelected = false; 
    }
    
    /**
       Animate the character
       */
    int imageIndex = 0;
    public void animate()
    {
        setImage(idle[imageIndex]); 
        imageIndex = (imageIndex + 1) % idle.length; 
    }
    
    int attackIndex = 0; 
    public void knifeAttack()
    {
      
            setImage(attack[attackIndex]); 
            attackIndex = (attackIndex + 1) % attack.length; 
        
    }
    
    //mouse movement 
    public void turnTowards (int x, int y)
    {
        double dx = x - getX();
        double dy = y - getY();
        double angle = Math.atan2(dy,dx)*180.0/Math.PI;
        setRotation( (int)angle );
    }
    public void mouseData(MouseInfo mi)
    {
        turnTowards(mi.getX(), mi.getY());
    }
    //
    
    //sprinting method 
    public void Sprint()
    {
        GameWorld playerWorld = (GameWorld) getWorld(); 
        
        //sprint bar 
        HUDsprintBar sprintingBar = playerWorld.sprintBar(); 
        if(Greenfoot.isKeyDown("Shift") && stamina > 5)
        {
             movementSpeed = 10;    
             stamina = stamina - 1; 
             sprintingBar.loseStamina(); 
             if(stamina <= 5)
             {
                stamina = 0;
                stamina = stamina + 0;
             }
             
        }
        else
        {
            movementSpeed = 5; 
            stamina = stamina + 1;
            sprintingBar.gainStamina(); 
            if(stamina >= 100){
                stamina = 100; 
                stamina = stamina + 0;
            }
            
        }
    }
    //
    
    public void soundCheck()
    {
        
        GreenfootSound slowRunning = new GreenfootSound("Minecraft Footsteps - Sound Effect (HD).wav"); 
        slowRunning.setVolume(50); 
    }
    //
    
    //move
    public void checkKeys()
    {
        //GreenfootSound running
        GreenfootSound running = new GreenfootSound("Valorant - Steps & Stepping - Gaming Sound Effect Valorant (HD) _ Sound Effects.wav");
        running.setVolume(50);
        if(Greenfoot.isKeyDown("w"))
        {
            /*if(!running.isPlaying())
            {
                running.play();
            }*/ //this method doesnt work!
            if(this.getY() > 50)
            {
                this.setLocation(this.getX(), this.getY() - movementSpeed);
            }
            animate(); 
        }
        else
        {
            moving = false; 
        }
       
        if(Greenfoot.isKeyDown("a"))
        {
            moving = true;
            if(this.getX() > 50)
            {
                this.setLocation(this.getX() - movementSpeed, this.getY()); 
            }
            
            animate(); 
        }
        else
        {
            moving = false; 
        }
        
        if(Greenfoot.isKeyDown("s"))
        {
            moving = true;
            if(this.getY() < 670)
            {
                this.setLocation(this.getX(), this.getY() + movementSpeed);    
            }
            animate(); 
        }
        else
        {
            moving = false; 
        }
        
        if(Greenfoot.isKeyDown("d"))
        {
            moving = true;
            if(this.getX() < 1150)
            {
                this.setLocation(this.getX() + movementSpeed, this.getY()); 
            } 
            animate(); 
        }
        else
        {
            moving = false; 
        }
        
        if(Greenfoot.isKeyDown("f") && knifeWait < 15)
        {   
            hitBox.active = true; 
            knifeAttack();
            
        }
        else
        {
            hitBox.active = false; 
        }
    }
   
    //
    public void worldEffects()
    {
        GreenfootSound lavaSound = new GreenfootSound("Player Fire Hurt (Nr. 3 _ Minecraft Sound) - Sound Effect for editing.wav");
        lavaSound.setVolume(75); 
        if(getWorld().getColorAt(getX(), getY()).equals(lava))
        {
            
            if (timer1.millisElapsed() > 750)
            {
                if (!lavaSound.isPlaying()) 
                {
                    lavaSound.play(); 
                }
                health = health - 10; 
                timer1.mark(); 
            }
        }
        if(getWorld().getColorAt(getX(), getY()).equals(lava2))
        {
            if (timer1.millisElapsed() > 750)
            {
                if (!lavaSound.isPlaying()) 
                {
                    lavaSound.play(); 
                }
                health = health - 10; 
                timer1.mark(); 
            }
        }
        if(getWorld().getColorAt(getX(), getY()).equals(lava3))
        {
            if (timer1.millisElapsed() > 750)
            {
                if (!lavaSound.isPlaying()) 
                {
                    lavaSound.play(); 
                }
                health = health - 10; 
                timer1.mark(); 
            }
        }
        if(getWorld().getColorAt(getX(), getY()).equals(lightGrass))
        {
            timer2.mark(); 
            movementSpeed = 2;
            if(timer2.millisElapsed() > 1000)
            {
                movementSpeed = 5; 
                timer2.mark(); 
            }
            moving = false;
            slowMoving = true; 
        }
        if(getWorld().getColorAt(getX(), getY()).equals(darkGrass))
        {
            timer2.mark(); 
            movementSpeed = 3;
            if (timer1.millisElapsed() > 750)
            {
                health = health - 5; 
                timer1.mark(); 
            }
            if(timer2.millisElapsed() > 1000)
            {
                movementSpeed = 5; 
                timer2.mark(); 
            }
            moving = false;
            slowMoving = true; 
        }
        if(getWorld().getColorAt(getX(), getY()).equals(hole))
        {
            timer2.mark(); 
            movementSpeed = 2;
            if(timer2.millisElapsed() > 1000)
            {
                movementSpeed = 5; 
                timer2.mark(); 
            }
            moving = false;
            slowMoving = true; 
        }
    }
    public void checkDamage()
    {
        if (timer.millisElapsed() > 250 )
        {
            GreenfootSound zombieDamage = new GreenfootSound("Horror Zombie Bite Sound Effect (No copyright sound effects) _ Sounds.wav");
            zombieDamage.setVolume(50);
            if(this.isTouching(Zombie.class) || this.isTouching(zombieBoss.class))
            {
                if (!zombieDamage.isPlaying()) 
                {
                    zombieDamage.play(); 
                }
                
                startingSurvivor.health = startingSurvivor.health - 5; 
            }
            timer.mark(); 
        }
    }
    //for power ups
    public void healthUp()
    {
        health = 100; 
    }
    public void staminaPowerUp()
    {
        stamina = 100; 
    }
    
    
    public void invincibility()
    {
        infiniteStamina.mark(); 
        infStamina = true; 
    }
    
    public void act() 
    {
        if(infiniteStamina.millisElapsed() < 5000 && infStamina == true)
        {
            health--; 
        }
        else
        {
            infStamina = false; 
        }
        knifeWait++;
        survivorX = getX();
        survivorY = getY();
        MouseInfo m = Greenfoot.getMouseInfo();   
        if(m != null)
        {
            mouseData(m);
        }
        if(knifeWait > 40)
        {
            knifeWait = 0; 
        }
        soundCheck(); 
        Sprint();   //sprint 
        checkDamage(); 
        worldEffects();
         
    
       
        checkKeys(); //move 

    }  
    
}


