package ru.geekbrains.homework5.mappers;

import ru.geekbrains.homework5.dto.IssueReportDto;
import ru.geekbrains.homework5.dto.IssueDto;
import ru.geekbrains.homework5.model.Book;
import ru.geekbrains.homework5.model.Issue;
import ru.geekbrains.homework5.model.Reader;

import java.util.List;

public class IssueMapper {

    /**
     * Mapped to Issue from IssueRequest
     * @param issueDto IssueRequest instance
     * @return Issue instance
     */
    public static Issue toIssue(IssueDto issueDto) {
        return new Issue(issueDto.getReaderId(), issueDto.getBookId());
    }

    /**
     * Mapped to Issue from IssueRequest
     * @param issue IssueRequest instance
     * @return Issue instance
     */
    public static IssueDto toIssueDto(Issue issue) {
        return new IssueDto(issue.getIdReader(), issue.getIdBook(), issue.getId());
    }

    /**
     * Mapped to Issue from IssueRequest
     * @param issues List
     * @return Issue instance
     */
    public static List<IssueDto> toReaderDtoList(List<Issue> issues) {
        return issues.stream().map(IssueMapper::toIssueDto).toList();
    }

    /**
     * Mapped to IssueDto from Issue, Book, Reader
     * @param issue
     * @param book
     * @param reader
     * @return
     */
    public static IssueReportDto toIssueReport(Issue issue,
                                               Book book,
                                               Reader reader) {
        return new IssueReportDto(book.getName(),
                reader.getName(),
                issue.getIssued_at(),
                issue.getReturned_at());
    }
}
