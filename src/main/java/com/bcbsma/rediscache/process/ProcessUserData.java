package com.bcbsma.rediscache.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bcbsma.rediscache.model.User;
import com.bcbsma.rediscache.model.UserAddress;
import com.bcbsma.rediscache.repo.UserRepository;

@Component
public class ProcessUserData {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	@Value("${data.folder}")
	private String dataFileFolder;

	@Value("${data.folder}")
	private String processedPath;

	@Autowired
	private UserRepository userRepository;

	@Scheduled(fixedRate = 20000) // Every 20 sec will execute
	public void readDataFile() throws FileNotFoundException, IOException {
		UserAddress ad;
		User user;
		File folder = new File(dataFileFolder);
		File[] listOfFiles = folder.listFiles();
		LOG.debug(" listOfFiles size is : " + listOfFiles.length);
		for (File file : listOfFiles) {
			if (file.isFile()) {
				LOG.debug(file.getName() + " is exist ");
				File dest = new File(processedPath + file.getName() + ".processed");
				List<UserAddress> adList = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader(file))) {
					String line;
					while ((line = br.readLine()) != null) {
						user = new User();
						user.setId(line.split(",")[0]);
						user.setName(line.split(",")[1]);
						user.setSalary(Long.valueOf(line.split(",")[2]));
						String s = line.split(",")[3].toString();
						List<String> addressList = Arrays.asList(s.split("::"));
						for (String adString : addressList) {
							ad = new UserAddress();
							ad.setStreetName1(adString.split(" ")[0]);
							ad.setStreetName2(adString.split(" ")[1]);
							ad.setCity(adString.split(" ")[2]);
							ad.setPinCode(adString.split(" ")[3]);
							adList.add(ad);
						}
						user.setAddress(adList);
						LOG.debug("User trying to save with userId : " + user.getId());
						userRepository.save(user);
						LOG.debug("User saved successfully with userId : " + user.getId());
					}
				}
				if (file.renameTo(dest))
					LOG.debug(file.getAbsolutePath() + " is processed successfully and renamed to "
							+ dest.getAbsolutePath());
			}
		}
	}
}
