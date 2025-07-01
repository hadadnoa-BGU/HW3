package utils.generators;

import java.util.Random;

public class NumberGenerator implements Generator 
{
    private Random random;

    public NumberGenerator()
    {
        random = new Random();
    }
    public NumberGenerator(long seed)
    {
        random = new Random(seed);
    }
    
    @Override
    public int generate(int value)
    {
        return random.nextInt(value);
    }
}
