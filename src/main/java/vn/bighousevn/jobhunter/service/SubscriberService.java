package vn.bighousevn.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.bighousevn.jobhunter.domain.Job;
import vn.bighousevn.jobhunter.domain.Skill;
import vn.bighousevn.jobhunter.domain.Subscriber;
import vn.bighousevn.jobhunter.domain.email.ResEmailJob;
import vn.bighousevn.jobhunter.repository.JobRepository;
import vn.bighousevn.jobhunter.repository.SkillRepository;
import vn.bighousevn.jobhunter.repository.SubscriberRepository;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final SkillRepository skillRepository;
    private final JobRepository jobRepository;
    private final EmailService emailService;

    public SubscriberService(SubscriberRepository subscriberRepository,
            SkillRepository skillRepository,
            JobRepository jobRepository,
            EmailService emailService) {
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
        this.jobRepository = jobRepository;
        this.emailService = emailService;
    }

    public boolean isSameEmail(String email) {
        return this.subscriberRepository.existsByEmail(email);
    }

    public Subscriber findById(long id) {
        Optional<Subscriber> subsOptional = this.subscriberRepository.findById(id);
        if (subsOptional.isPresent())
            return subsOptional.get();
        return null;
    }

    public Subscriber handleCreateSubscriber(Subscriber s) {

        List<Long> reqList = s.getSkills()
                .stream().map(item -> item.getId())
                .collect(Collectors.toList());

        List<Skill> skillList = this.skillRepository.findByIdIn(reqList);
        s.setSkills(skillList);

        return this.subscriberRepository.save(s);

    }

    public Subscriber handleUpdateSubscriber(Subscriber subsDB, Subscriber s) {

        if (subsDB.getSkills() != null) {
            List<Long> reqList = s.getSkills().stream()
                    .map(item -> item.getId()).collect(Collectors.toList());

            List<Skill> skillList = this.skillRepository.findByIdIn(reqList);

            subsDB.setSkills(skillList);
        }
        return this.subscriberRepository.save(subsDB);
    }

    public ResEmailJob convertJobToSendEmail(Job job) {
        ResEmailJob res = new ResEmailJob();
        res.setName(job.getName());
        res.setSalary(job.getSalary());
        res.setCompany(new ResEmailJob.CompanyEmail(job.getCompany().getName()));
        List<Skill> skills = job.getSkills();
        List<ResEmailJob.SkillEmail> s = skills.stream().map(skill -> new ResEmailJob.SkillEmail(skill.getName()))
                .collect(Collectors.toList());
        res.setSkills(s);
        return res;
    }

    public void sendSubscribersEmailJobs() {

        List<Subscriber> listSubs = this.subscriberRepository.findAll();
        if (listSubs != null && listSubs.size() > 0) {
            for (Subscriber sub : listSubs) {
                List<Skill> listSkills = sub.getSkills();
                if (listSkills != null && listSkills.size() > 0) {
                    List<Job> listJobs = this.jobRepository.findBySkillsIn(listSkills);
                    if (listJobs != null && listJobs.size() > 0) {

                        List<ResEmailJob> arr = listJobs.stream().map(
                                job -> this.convertJobToSendEmail(job)).collect(Collectors.toList());

                        this.emailService.sendEmailFromTemplateSync(
                                sub.getEmail(),
                                "Cơ hội việc làm hot đang chờ đón bạn, khám phá ngay",
                                "job",
                                sub.getName(),
                                arr);
                    }
                }
            }
        }
    }

    public Subscriber findByEmail(String email) {
        return this.subscriberRepository.findByEmail(email);
    }

}
