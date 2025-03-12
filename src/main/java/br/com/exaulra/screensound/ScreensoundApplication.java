package br.com.exaulra.screensound;

import br.com.exaulra.screensound.main.Main;
import br.com.exaulra.screensound.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreensoundApplication implements CommandLineRunner {

	public static void main(String[] args)  {
		SpringApplication.run(ScreensoundApplication.class, args);
	}

	@Autowired
	private ArtistRepository artistRepository;

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(this.artistRepository);
		main.menu();
	}
}
