
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class survivorIdleKnife here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class startingSurvivor extends Actor
{
    public static int survivorX, survivorY, wait, knifeWait, health, cooldownShootingHandgun, cooldownShootingShotgun, cooldownShootingRifle;
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
    
    //reload
    public boolean canReload;
    private boolean rDown; 
       
    //variable to keep track which gun is selected 
    public static boolean pistolSelected = false; 
    public static boolean rifleSelected = false; 
    public static boolean shotgunSelected = false; 
    /**
     * Act - do whatever the survivorIdleKnife wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public startingSurvivor()
    {
        wait = 0; 
        knifeWait = 0; 
        health = 100; 
        cooldownShootingHandgun = 11;
        cooldownShootingShotgun = 95; 
        cooldownShootingRifle = 6;
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
    
    //shoot & reload
    public void HandgunShoot()
    {
        Projectile bullet = new Projectile();
        GreenfootSound HandgunShotSound = new GreenfootSound("handgun shot.mp3"); 
        GreenfootSound EmptyHandgunShotSound = new GreenfootSound("empty handgun sound.mp3"); 
        GameWorld playerWorld = (GameWorld) getWorld(); 
        if(Greenfoot.isKeyDown("Space") && wait > cooldownShootingHandgun && playerWorld.ammoMagazineIndicator() != 0)
        {
            HandgunShotSound.setVolume(30); 
            HandgunShotSound.play(); 
            
            
            wait = 0; 
            bullet.setRotation(getRotation());
            getWorld().addObject(bullet, getX(), getY()); 
            bullet.move(50); 
            bullet.setImage(Projectile.bullet);  
            
            playerWorld.ammoMagazine(); 
            
            
        }
        if(Greenfoot.isKeyDown("Space") && wait > cooldownShootingHandgun && playerWorld.ammoMagazineIndicator() == 0)
        {
            wait = 0; 
            EmptyHandgunShotSound.setVolume(50); 
            EmptyHandgunShotSound.play(); 
        }
    }
    public void reloadHandgun()
    {
        GameWorld playerWorld = (GameWorld) getWorld(); 
        GreenfootSound handgunReloadSound = new GreenfootSound("handgun reload.mp3"); 
        if(!rDown && playerWorld.ammoMagazineIndicator() != 7 && Greenfoot.isKeyDown("r") && playerWorld.ammoTotalIndicator() > 0)
        {
            rDown = true; 
            handgunReloadSound.setVolume(30); 
            handgunReloadSound.play(); 
            /*
            while(handgunReloadSound.isPlaying())
            {
               canReload = false;  
            }
            /*
            canReload = true;
        
            if(canReload)
            {
                playerWorld.addAmmoMagazine();  
            } 
            */
            playerWorld.addAmmoMagazine();  
        }
        if(rDown && !Greenfoot.isKeyDown("r"))
        {
            rDown = false; 
        }
    }
    
    public void ShotgunShoot()
    {
        Projectile bullet = new Projectile();
        Projectile bullet2 = new Projectile();
        Projectile bullet3 = new Projectile();
        GameWorld playerWorld = (GameWorld) getWorld(); 
        GreenfootSound shotgunShotSound = new GreenfootSound("pump shotgun shot.mp3"); 
        GreenfootSound EmptyshotgunShotSound = new GreenfootSound("shotgun dry firing.mp3"); 
        if(Greenfoot.isKeyDown("Space") && wait > cooldownShootingShotgun && playerWorld.ammoMagazineIndicatorShotgun() != 0)
        {
            shotgunShotSound.setVolume(50);
            shotgunShotSound.play(); 
            
            wait = 0; 
            
            bullet.setRotation(getRotation()+5);
            getWorld().addObject(bullet, getX(), getY()); 
            bullet.move(50); 
            bullet.setImage(Projectile.bullet);  
            
            bullet2.setRotation(getRotation());
            getWorld().addObject(bullet2, getX(), getY()); 
            bullet2.move(50); 
            bullet2.setImage(Projectile.bullet);  
            
            bullet3.setRotation(getRotation()-5);
            getWorld().addObject(bullet3, getX(), getY()); 
            bullet3.move(50); 
            bullet3.setImage(Projectile.bullet);  
            
            
            playerWorld.ammoMagazineShotgun(); 
            
            
        }
        if(Greenfoot.isKeyDown("Space") && wait > cooldownShootingShotgun && playerWorld.ammoMagazineIndicatorShotgun() == 0)
        {
            wait = 0;
            EmptyshotgunShotSound.setVolume(50);
            EmptyshotgunShotSound.play(); 
        }
    }
    public void reloadShotgun()
    {
        GameWorld playerWorld = (GameWorld) getWorld();
        GreenfootSound shotgunReloadSound = new GreenfootSound("shotgun reload.mp3"); 
        if(!rDown && playerWorld.ammoMagazineIndicatorShotgun() != 8 && Greenfoot.isKeyDown("r") && playerWorld.ammoTotalIndicatorShotgun() > 0)
        {
            rDown = true; 
            shotgunReloadSound.setVolume(30); 
            shotgunReloadSound.play(); 
            /*
            while(shotgunReloadSound.isPlaying())
            {
               canReload = false;  
            }
            canReload = true;
        
            if(canReload)
            {
                playerWorld.addAmmoMagazineShotgun();     
            }
            */
           playerWorld.addAmmoMagazineShotgun();   
        }
        if(rDown && !Greenfoot.isKeyDown("r"))
        {
            rDown = false; 
        }
    }
    
    public void RifleShoot()
    {
        Projectile bullet = new Projectile();
        GameWorld playerWorld = (GameWorld) getWorld(); 
        GreenfootSound RifleShotSound = new GreenfootSound("rifle gun sounds.mp3"); 
        GreenfootSound EmptyRifleShotSound = new GreenfootSound("rifle dry fire.mp3");
        if(Greenfoot.isKeyDown("Space") && wait > cooldownShootingRifle && playerWorld.ammoMagazineIndicatorRifle() != 0)
        {
            RifleShotSound.setVolume(30);
            RifleShotSound.play(); 
                        
            wait = 0;
            bullet.setRotation(getRotation());
            getWorld().addObject(bullet, getX(), getY()); 
            bullet.move(50); 
            bullet.setImage(Projectile.bullet);  
            
            playerWorld.ammoMagazineRifle(); 
            
            
        }
        if(Greenfoot.isKeyDown("Space") && wait > cooldownShootingRifle && playerWorld.ammoMagazineIndicatorRifle() == 0)
        {
            wait = 0;
            EmptyRifleShotSound.play(); 
        }
        
    }
    public void reloadRifle()
    {
        GameWorld playerWorld = (GameWorld) getWorld(); 
        GreenfootSound rifleReloadSound = new GreenfootSound("rifle reload.mp3"); 
        if(!rDown && playerWorld.ammoMagazineIndicatorRifle() != 30 && Greenfoot.isKeyDown("r") && playerWorld.ammoTotalIndicatorRifle() > 0)
        {
            rifleReloadSound.setVolume(30); 
            rifleReloadSound.play(); 
            /*
            while(rifleReloadSound.isPlaying())
            {
               canReload = false;  
            }
            canReload = true;
        
            if(canReload)
            {
                playerWorld.addAmmoMagazineRifle();       
            }
            */
            playerWorld.addAmmoMagazineRifle();    
        }
        if(rDown && !Greenfoot.isKeyDown("r"))
        {
            rDown = false; 
        }
    }
    //
    
    //move
    public void checkKeys()
    {
        if(Greenfoot.isKeyDown("w"))
        {
            if(this.getY() > 50)
            {
                this.setLocation(this.getX(), this.getY() - movementSpeed);
            }
            animate(); 
        }
       
        if(Greenfoot.isKeyDown("a"))
        {
            if(this.getX() > 50)
            {
                this.setLocation(this.getX() - movementSpeed, this.getY()); 
            }
            animate(); 
        }
        
        if(Greenfoot.isKeyDown("s"))
        {
            if(this.getY() < 670)
            {
                this.setLocation(this.getX(), this.getY() + movementSpeed);    
            }
            animate(); 
        }
        
        if(Greenfoot.isKeyDown("d"))
        {
            if(this.getX() < 1150)
            {
                this.setLocation(this.getX() + movementSpeed, this.getY()); 
            } 
            animate(); 
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
    
        if(Greenfoot.isKeyDown("1"))
        {
            pistolSelected = false; 
            rifleSelected = false; 
            shotgunSelected = false; 
        }        
        if(Greenfoot.isKeyDown("2"))
        {
            pistolSelected = true; 
            rifleSelected = false; 
            shotgunSelected = false; 
        }
        if(Greenfoot.isKeyDown("3"))
        {
            pistolSelected = false; 
            rifleSelected = true; 
            shotgunSelected = false; 
        }
        if(Greenfoot.isKeyDown("4"))
        {
            pistolSelected = false; 
            rifleSelected = false; 
            shotgunSelected = true; 
        }
        
        /*
        if(Greenfoot.isKeyDown("1"))
        {
            health = 100;            
        }
        if(Greenfoot.isKeyDown("2"))
        {
            cooldownShootingHandgun = 5;
        }
        if(Greenfoot.isKeyDown("3"))
        {
            stamina = 1000;
        }
        if(Greenfoot.isKeyDown("4"))
        {
            GreenfootImage monkey = new GreenfootImage(5, 5); 
            Projectile.bullet = monkey; 
            Projectile.bullet = Projectile.bigBullet; 
        }
        if(Greenfoot.isKeyDown("5"))
        {
            while(true)
            {
                health = 100; 
            }
        }
         */
    }
   
    //
    public void worldEffects()
    {
        if (timer.millisElapsed() > 250)
        {
            if(this.isTouching(Zombie.class))
            {
                health = health - 10; 
            }
            timer.mark(); 
        }
        if(getWorld().getColorAt(getX(), getY()).equals(lava))
        {
            if (timer1.millisElapsed() > 750)
            {
                health = health - 10; 
                timer1.mark(); 
            }
        }
        if(getWorld().getColorAt(getX(), getY()).equals(lava2))
        {
            if (timer1.millisElapsed() > 750)
            {
                health = health - 10; 
                timer1.mark(); 
            }
        }
        if(getWorld().getColorAt(getX(), getY()).equals(lava3))
        {
            if (timer1.millisElapsed() > 750)
            {
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
        }
    }
    
    public void healthSet()
    {
        health = 100; 
    }
    
    public void act() 
    {
        wait++; 
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

        Sprint();   //sprint 
        worldEffects();
         
        if(pistolSelected == true)
        {
            HandgunShoot(); 
            reloadHandgun(); 
        }
        if(rifleSelected == true)
        {
            RifleShoot(); 
            reloadRifle();
        }
        if(shotgunSelected == true)
        {
            ShotgunShoot(); 
            reloadShotgun();
        }
        
        checkKeys(); //move 

    }  
    
}


